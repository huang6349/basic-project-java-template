import { authenticate, unAuthenticate, account, authorities } from '@/services/users';

export default {
  state: {
    isLogin: null,
    username: '无',
    menuData: [],
  },
  subscriptions: {},
  effects: {
    *login({ payload }, { put, call, select }) {
      const isLogin = yield call(authenticate, payload) || {};
      yield put({ type: 'updateState', payload: { isLogin } });
    },
    *logout({ payload }, { put, call, select }) {
      yield call(unAuthenticate, payload) || {};
      yield put({ type: 'updateState', payload: { isLogin: !1 } });
    },
    *keep({ payload }, { put, call, select }) {
      const { hasToken } = payload;
      yield put({ type: 'updateState', payload: { isLogin: hasToken } });
    },
    *fetchUser({ payload }, { put, call, select }) {
      const {
        data: { username, nickname, realname },
      } = yield call(account) || {};
      const { data: menuData = [] } = yield call(authorities) || {};
      yield put({ type: 'updateState', payload: { username: realname || nickname || username, menuData } });
    },
  },
  reducers: {
    updateState: function(state, { payload }) {
      return { ...state, ...payload };
    },
  },
};
