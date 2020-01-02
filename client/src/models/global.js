import { authenticate } from '@/services/users';

export default {
  state: {
    isLogin: !1,
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
  },
  reducers: {
    update_state: function(state, { payload }) {
      return { ...state, ...payload };
    },
  },
};
