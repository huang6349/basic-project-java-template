import localforage from 'localforage';

import { nprogress } from '@/utils';

export default {
  errorConfig: {
    adaptor: function(resData) {
      return { ...resData, errorCode: resData['state'], errorMessage: resData['message'] };
    },
  },
  requestInterceptors: [
    function interceptorNprogress(url, options) {
      nprogress.start();
      return { url, options };
    },
    async function interceptorToken(url, options) {
      options['headers'] = options['headers'] || {};
      const token = await localforage.getItem(TOKEN_NAME);
      if (token) options['headers']['Authorization'] = token;
      return { url, options };
    },
  ],
  responseInterceptors: [
    function interceptorNprogress(response) {
      nprogress.done();
      return response;
    },
    async function interceptorToken(response) {
      if (response['status'] === 401) {
        await localforage.removeItem(TOKEN_NAME);
      }
      if (response['headers'].get('Authorization')) {
        await localforage.setItem(TOKEN_NAME, response['headers'].get('Authorization'));
      }
      return response;
    },
  ],
};
