import NProgress from 'nprogress';
import localforage from 'localforage';
import { extend } from 'umi-request';
import { message } from 'antd';
import { TOKEN } from '@/constant';
import 'nprogress/nprogress.css';

NProgress.configure({ showSpinner: false });

const request = extend({ credentials: 'include' });

request.interceptors.request.use(async (url, options) => {
  NProgress.start();
  options.headers = options.headers || {};
  options.headers['Content-Type'] = 'application/json;charset=UTF-8';
  options.headers['Accept'] = 'application/json';
  const token = await localforage.getItem(TOKEN['name']);
  if (token) {
    options.headers['Authorization'] = `Bearer ${token}`;
  }
  NProgress.inc();
  return { url: `/api${url}`, options };
});

request.interceptors.response.use(async (response, ...b) => {
  NProgress.done();
  const data = await response.json();
  if (data && data['success']) {
    return data['data'];
  } else {
    message.error(data['message']);
    return {};
  }
});

export default request;
