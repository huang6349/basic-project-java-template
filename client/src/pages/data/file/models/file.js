import pathToRegexp from 'path-to-regexp';
import { queryFileByPageable } from '@/services/file';

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
        const match = pathToRegexp('/data/file').exec(pathname);
        if (!match) return;
        dispatch({ type: 'fetchFile', payload: { init: !0 } });
      });
    },
  },
  effects: {
    *fetchFile({ payload }, { select, call, put }) {
      const { current: dc, pageSize: dp, search: ds } = yield select(({ file }) => file);
      const { current, pageSize, search, init } = payload;
      const { data, params: { page, size, total } = {} } = yield call(queryFileByPageable, {
        page: ((init ? dc : current) || 1) - 1,
        size: (init ? dp : pageSize) || 10,
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
  },
  reducers: {
    updateState: function(state, { payload }) {
      return { ...state, ...payload };
    },
  },
};
