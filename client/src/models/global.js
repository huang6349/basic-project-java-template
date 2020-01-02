import { authenticate, authorities } from '@/services/users';

export default {
  state: {
    isLogin: null,
    menuData: [],
  },
  subscriptions: {},
  effects: {
    *login({ payload }, { put, call, select }) {
      const isLogin = yield call(authenticate, payload) || {};
      yield put({ type: 'update_state', payload: { isLogin } });
    },
    *keep({ payload }, { put, call, select }) {
      const { hasToken } = payload;
      yield put({ type: 'update_state', payload: { isLogin: hasToken } });
    },
    *fetchUser({ payload }, { put, call, select }) {
      const menuData = yield call(authorities) || [];
      yield put({ type: 'update_state', payload: { menuData } });
    },
  },
  reducers: {
    update_state: function(state, { payload }) {
      return { ...state, ...payload };
    },
  },
};
