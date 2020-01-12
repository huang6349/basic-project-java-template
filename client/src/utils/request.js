import { message } from 'antd';
import NProgress from 'nprogress';
import localforage from 'localforage';
import { extend } from 'umi-request';
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

request.interceptors.response.use(async (response, { method }) => {
  NProgress.done();
  const data = await response.clone().json();
  if (data && data['success']) {
    method !== 'GET' && message.success(data['message']);
    return response;
  } else {
    const error = new Error(data['message']);
    error.response = data;
    throw error;
  }
});

export default request;
