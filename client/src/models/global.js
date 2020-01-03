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
      yield put({ type: 'updateState', payload: { isLogin } });
    },
    *keep({ payload }, { put, call, select }) {
      const { hasToken } = payload;
      yield put({ type: 'updateState', payload: { isLogin: hasToken } });
    },
    *fetchUser({ payload }, { put, call, select }) {
      const { data: menuData = [] } = yield call(authorities) || {};
      yield put({ type: 'updateState', payload: { menuData } });
    },
  },
  reducers: {
    updateState: function(state, { payload }) {
      return { ...state, ...payload };
    },
  },
};
