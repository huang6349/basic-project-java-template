import pathToRegexp from 'path-to-regexp';
import { createAuthority, queryAuthorityByPageable, updateAuthority, deleteAuthority } from '@/services/authority';

export default {
  state: {
    current: 1,
    pageSize: 10,
    total: 0,
    list: [],
  },
  subscriptions: {
    setup: function({ dispatch, history }) {
      history.listen(function({ pathname }) {
        const match = pathToRegexp('/system/authority').exec(pathname);
        if (!match) return;
        dispatch({ type: 'fetchAuthority', payload: { init: !0 } });
      });
    },
  },
  effects: {
    *fetchAuthority({ payload }, { select, call, put }) {
      const { current: dc, pageSize: dp, search: ds } = yield select(({ authority }) => authority);
      const { current, pageSize, search, init } = payload;
      const { data, params: { page, size, total } = {} } = yield call(queryAuthorityByPageable, {
        page: ((init ? dc : current) || 1) - 1,
        size: (init ? dp : pageSize) || 10,
        name: init ? ds : search,
      });
      yield put({
        type: 'updateState',
        payload: {
          current: page,
          pageSize: size,
          total: total,
          list: data,
          search: init ? ds : search,
        },
      });
    },
    *createAuthority({ payload }, { select, call, put }) {
      const { pageSize } = yield select(({ authority }) => authority);
      yield call(createAuthority, payload);
      yield put({ type: 'fetchAuthority', payload: { pageSize } });
    },
    *updateAuthority({ payload }, { select, call, put }) {
      const { current, pageSize, search } = yield select(({ authority }) => authority);
      yield call(updateAuthority, payload);
      yield put({ type: 'fetchAuthority', payload: { current, pageSize, search } });
    },
    *deleteAuthority({ payload }, { select, call, put }) {
      const { current, pageSize, search } = yield select(({ authority }) => authority);
      yield call(deleteAuthority, payload['id']);
      yield put({ type: 'fetchAuthority', payload: { current, pageSize, search } });
    },
  },
  reducers: {
    updateState: function(state, { payload }) {
      return { ...state, ...payload };
    },
  },
};
