import { authenticate, unAuthenticate, account, authorities, changePassword } from '@/services/users';
import { localDownload } from '@/services/download';

export default {
  state: {
    isLogin: null,
    username: '无',
    menuData: [],
  },
  subscriptions: {},
  effects: {
    *login({ payload }, { put, call, select }) {
      const { success } = yield call(authenticate, payload) || {};
      yield put({ type: 'updateState', payload: { isLogin: success } });
    },
    *logout({ payload }, { put, call, select }) {
      yield call(unAuthenticate, payload) || {};
      yield put({ type: 'updateState', payload: { isLogin: !1, menuData: [] } });
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
    *changePassword({ payload }, { put, call, select }) {
      yield call(changePassword, payload) || {};
    },
    *localDownload({ payload }, { put, call, select }) {
      yield call(localDownload, payload['url']);
    },
  },
  reducers: {
    updateState: function(state, { payload }) {
      return { ...state, ...payload };
    },
  },
};
