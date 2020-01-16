import { createDict, queryDictByPageable, updateDict, deleteDict } from '@/services/dict';

export default {
  state: {
    current: 1,
    pageSize: 10,
    total: 0,
    list: [],
  },
  subscriptions: {},
  effects: {
    *fetchDict({ payload }, { select, call, put }) {
      const { current: dc, pageSize: dp, pid: di, search: ds } = yield select(({ dictConfig }) => dictConfig);
      const { current, pageSize, search, init, pid } = payload;
      const { data, params: { page, size, total } = {} } = yield call(queryDictByPageable, {
        page: ((init ? dc : current) || 1) - 1,
        size: (init ? dp : pageSize) || 10,
        pid: pid || di,
        name: init ? ds : search,
      });
      yield put({
        type: 'updateState',
        payload: {
          current: page + 1,
          pageSize: size,
          total: total,
          list: data,
          pid: pid || di,
          search: init ? ds : search,
        },
      });
    },
    *createDict({ payload }, { select, call, put }) {
      const { pageSize, pid } = yield select(({ dictConfig }) => dictConfig);
      yield call(createDict, { ...payload, pid });
      yield put({ type: 'fetchDict', payload: { pageSize } });
    },
    *updateDict({ payload }, { select, call, put }) {
      const { current, pageSize, search, pid } = yield select(({ dictConfig }) => dictConfig);
      yield call(updateDict, { ...payload, pid });
      yield put({ type: 'fetchDict', payload: { current, pageSize, search } });
    },
    *deleteDict({ payload }, { select, call, put }) {
      const { current, pageSize, search } = yield select(({ dictConfig }) => dictConfig);
      yield call(deleteDict, payload['id']);
      yield put({ type: 'fetchDict', payload: { current, pageSize, search } });
    },
  },
  reducers: {
    updateState: function(state, { payload }) {
      return { ...state, ...payload };
    },
    resetState: function() {
      return { current: 1, pageSize: 10, total: 0, list: [] };
    },
  },
};
