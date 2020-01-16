import pathToRegexp from 'path-to-regexp';
import { createDict, queryDictByPageable, updateDict, deleteDict } from '@/services/dict';

export default {
  state: {
    current: 1,
    pageSize: 10,
    total: 0,
    list: [],
    pid: 0,
  },
  subscriptions: {
    setup: function({ dispatch, history }) {
      history.listen(function({ pathname }) {
        const match = pathToRegexp('/data/dict').exec(pathname);
        if (!match) return;
        dispatch({ type: 'fetchDict', payload: { init: !0 } });
      });
    },
  },
  effects: {
    *fetchDict({ payload }, { select, call, put }) {
      const { current: dc, pageSize: dp, pid, search: ds } = yield select(({ dict }) => dict);
      const { current, pageSize, search, init } = payload;
      const { data, params: { page, size, total } = {} } = yield call(queryDictByPageable, {
        page: ((init ? dc : current) || 1) - 1,
        size: (init ? dp : pageSize) || 10,
        pid: pid,
        name: init ? ds : search,
      });
      yield put({
        type: 'updateState',
        payload: {
          current: page + 1,
          pageSize: size,
          total: total,
          list: data,
          search: init ? ds : search,
        },
      });
    },
    *createDict({ payload }, { select, call, put }) {
      const { pageSize } = yield select(({ dict }) => dict);
      yield call(createDict, payload);
      yield put({ type: 'fetchDict', payload: { pageSize } });
    },
    *updateDict({ payload }, { select, call, put }) {
      const { current, pageSize, search } = yield select(({ dict }) => dict);
      yield call(updateDict, payload);
      yield put({ type: 'fetchDict', payload: { current, pageSize, search } });
    },
    *deleteDict({ payload }, { select, call, put }) {
      const { current, pageSize, search } = yield select(({ dict }) => dict);
      yield call(deleteDict, payload['id']);
      yield put({ type: 'fetchDict', payload: { current, pageSize, search } });
    },
  },
  reducers: {
    updateState: function(state, { payload }) {
      return { ...state, ...payload };
    },
  },
};
