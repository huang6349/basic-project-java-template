import { createResource, queryResourceByPageable, updateResource, deleteResource } from '@/services/resource';
import { queryDictToChildren } from '@/services/dict';

export default {
  state: {
    current: 1,
    pageSize: 10,
    total: 0,
    list: [],
    methods: [],
  },
  subscriptions: {},
  effects: {
    *fetchResource({ payload }, { select, call, put }) {
      const { current: dc, pageSize: dp, pid: di, search: ds } = yield select(({ resource }) => resource);
      const { current, pageSize, search, init, pid } = payload;
      const { data, params: { page, size, total } = {} } = yield call(queryResourceByPageable, {
        page: ((init ? dc : current) || 1) - 1,
        size: (init ? dp : pageSize) || 10,
        permissionsId: pid || di,
        pattern: init ? ds : search,
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
    *fetchMethods({ payload }, { select, call, put }) {
      const { data = [] } = yield call(queryDictToChildren, 'SYS.METHOD');
      yield put({ type: 'updateState', payload: { methods: data } });
    },
    *createResource({ payload }, { select, call, put }) {
      const { pageSize, pid } = yield select(({ resource }) => resource);
      yield call(createResource, { ...payload, permissionsId: pid });
      yield put({ type: 'fetchResource', payload: { pageSize } });
    },
    *updateResource({ payload }, { select, call, put }) {
      const { current, pageSize, search, pid } = yield select(({ resource }) => resource);
      yield call(updateResource, { ...payload, permissionsId: pid });
      yield put({ type: 'fetchResource', payload: { current, pageSize, search } });
    },
    *deleteResource({ payload }, { select, call, put }) {
      const { current, pageSize, search } = yield select(({ resource }) => resource);
      yield call(deleteResource, payload['id']);
      yield put({ type: 'fetchResource', payload: { current, pageSize, search } });
    },
  },
  reducers: {
    updateState: function(state, { payload }) {
      return { ...state, ...payload };
    },
    resetState: function() {
      return {
        current: 1,
        pageSize: 10,
        total: 0,
        list: [],
        methods: [],
      };
    },
  },
};
