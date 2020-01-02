import localforage from 'localforage';
import { TOKEN } from '@/constant';
import request from '@/utils/request';

export async function authenticate(login) {
  const { id_token } = await request.post('/authenticate', { data: login });
  if (id_token) {
    await localforage.setItem(TOKEN['name'], id_token);
    return !0;
  } else {
    return !1;
  }
}

export async function authorities() {
  return await request.get('/authorities/tree');
}
