import localforage from 'localforage';

import { nprogress } from '@/utils';

export default {
  errorConfig: {
    adaptor: function(resData) {
      return { ...resData, errorCode: resData['state'], errorMessage: resData['message'] };
    },
  },
  requestInterceptors: [
    async function requestInterceptorToken(url, options) {
      options.headers = options.headers || {};
      const token = await localforage.getItem(TOKEN_NAME);
      if (token) options.headers['Authorization'] = token;
      return { url, options };
    },
    function requestInterceptorNprogress(url, options) {
      nprogress.start();
      return { url, options };
    },
  ],
  responseInterceptors: [
    async function responseInterceptorToken(response) {
      if (response.headers.get('Authorization')) {
        await localforage.setItem(TOKEN_NAME, response.headers.get('Authorization'));
      }
      return response;
    },
    function responseInterceptorNprogress(response) {
      nprogress.done();
      return response;
    },
  ],
};
