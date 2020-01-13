import { message } from 'antd';
import NProgress from 'nprogress';
import localforage from 'localforage';
import { extend } from 'umi-request';
import { TOKEN } from '@/constant';
import 'nprogress/nprogress.css';

NProgress.configure({ showSpinner: false });

const request = extend({ credentials: 'include' });

request.use(async (ctx, next) => {
  const { req } = ctx;
  const { url, options = {} } = req;
  if (url.indexOf('/api') !== 0) ctx.req.url = `/api${url}`;
  options.headers = options.headers || {};
  options.headers['Content-Type'] = 'application/json;charset=UTF-8';
  options.headers['Accept'] = 'application/json';
  const token = await localforage.getItem(TOKEN['name']);
  if (token) options.headers['Authorization'] = `Bearer ${token}`;
  ctx.req.options = options;
  NProgress.start();
  NProgress.inc();
  await next();
  NProgress.done();
  const { res = {} } = ctx;
  const { method } = options;
  if (res['success'] && method !== 'GET') {
    message.success(res['message']);
  }
  if (!res['success']) {
    const error = new Error(res['message']);
    error.data = res;
    throw error;
  }
});

export default request;
