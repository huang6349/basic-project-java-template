import pathToRegexp from 'path-to-regexp';
import {
  createUser,
  queryUserByPageable,
  updateUser,
  deleteUser,
  enableUser,
  disableUser,
  resetPassword,
} from '@/services/users';
import { queryAuthority } from '@/services/authority';
import { queryDictToChildren } from '@/services/dict';

export default {
  state: {
    current: 1,
    pageSize: 10,
    total: 0,
    list: [],
    authoritys: [],
    sexs: [],
  },
  subscriptions: {
    setup: function({ dispatch, history }) {
      history.listen(function({ pathname }) {
        const match = pathToRegexp('/system/users').exec(pathname);
        if (!match) return;
        dispatch({ type: 'resetState' });
        dispatch({ type: 'fetchUsers', payload: { init: !0 } });
        dispatch({ type: 'fetchAuthoritys' });
        dispatch({ type: 'fetchSexs' });
      });
    },
  },
  effects: {
    *fetchUsers({ payload }, { select, call, put }) {
      const { current: dc, pageSize: dp, search: ds } = yield select(({ users }) => users);
      const { current, pageSize, search, init } = payload;
      const { data, params: { page, size, total } = {} } = yield call(queryUserByPageable, {
        page: ((init ? dc : current) || 1) - 1,
        size: (init ? dp : pageSize) || 10,
        username: init ? ds : search,
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
    *fetchAuthoritys({ payload }, { select, call, put }) {
      const { data = [] } = yield call(queryAuthority);
      yield put({ type: 'updateState', payload: { authoritys: data } });
    },
    *fetchSexs({ payload }, { select, call, put }) {
      const { data = [] } = yield call(queryDictToChildren, 'SYS.SEX');
      yield put({ type: 'updateState', payload: { sexs: data } });
    },
    *createUser({ payload }, { select, call, put }) {
      const { pageSize } = yield select(({ users }) => users);
      yield call(createUser, payload);
      yield put({ type: 'fetchUsers', payload: { pageSize } });
    },
    *updateUser({ payload }, { select, call, put }) {
      const { current, pageSize, search } = yield select(({ users }) => users);
      yield call(updateUser, payload);
      yield put({ type: 'fetchUsers', payload: { current, pageSize, search } });
    },
    *deleteUser({ payload }, { select, call, put }) {
      const { current, pageSize, search } = yield select(({ users }) => users);
      yield call(deleteUser, payload['id']);
      yield put({ type: 'fetchUsers', payload: { current, pageSize, search } });
    },
    *enableUser({ payload }, { select, call, put }) {
      const { current, pageSize, search } = yield select(({ users }) => users);
      yield call(enableUser, payload['id']);
      yield put({ type: 'fetchUsers', payload: { current, pageSize, search } });
    },
    *disableUser({ payload }, { select, call, put }) {
      const { current, pageSize, search } = yield select(({ users }) => users);
      yield call(disableUser, payload['id']);
      yield put({ type: 'fetchUsers', payload: { current, pageSize, search } });
    },
    *resetPassword({ payload }, { select, call, put }) {
      yield call(resetPassword, payload['id']);
    },
  },
  reducers: {
    updateState: function(state, { payload }) {
      return { ...state, ...payload };
    },
    resetState: function(state) {
      return { ...state, authoritys: [], sexs: [] };
    },
  },
};
