import pathToRegexp from 'path-to-regexp';
import {
  createPermissions,
  queryPermissionsToTree,
  updatePermissions,
  deletePermissions,
} from '@/services/permissions';

export default {
  state: {
    list: [],
  },
  subscriptions: {
    setup: function({ dispatch, history }) {
      history.listen(function({ pathname }) {
        const match = pathToRegexp('/system/permissions').exec(pathname);
        if (!match) return;
        dispatch({ type: 'fetchPermissions', payload: { init: !0 } });
      });
    },
  },
  effects: {
    *fetchPermissions({ payload }, { select, call, put }) {
      const { search: ds } = yield select(({ permissions }) => permissions);
      const { search, init } = payload;
      const { data } = yield call(queryPermissionsToTree, {
        name: init ? ds : search,
      });
      yield put({
        type: 'updateState',
        payload: {
          list: data,
          search: init ? ds : search,
        },
      });
    },
    *createPermissions({ payload }, { select, call, put }) {
      yield call(createPermissions, payload);
      yield put({ type: 'fetchPermissions', payload: {} });
      yield put({ type: 'global/fetchUser', payload: {} });
    },
    *updatePermissions({ payload }, { select, call, put }) {
      const { search } = yield select(({ permissions }) => permissions);
      yield call(updatePermissions, payload);
      yield put({ type: 'fetchPermissions', payload: { search } });
      yield put({ type: 'global/fetchUser', payload: {} });
    },
    *deletePermissions({ payload }, { select, call, put }) {
      const { search } = yield select(({ permissions }) => permissions);
      yield call(deletePermissions, payload['id']);
      yield put({ type: 'fetchPermissions', payload: { search } });
      yield put({ type: 'global/fetchUser', payload: {} });
    },
  },
  reducers: {
    updateState: function(state, { payload }) {
      return { ...state, ...payload };
    },
  },
};
