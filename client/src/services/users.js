import localforage from 'localforage';
import { TOKEN } from '@/constant';
import request from '@/utils/request';

export async function authenticate(login) {
  const { success, data: { id_token } = {} } = await request.post('/authenticate', { data: login });
  if (success) {
    await localforage.setItem(TOKEN['name'], id_token);
  }
  return success;
}

export async function unAuthenticate() {
  await localforage.removeItem(TOKEN['name']);
}

export async function account() {
  return await request.get('/account');
}

export async function authorities() {
  return await request.get('/authorities/tree');
}

export async function changePassword(password) {
  return await request.post('/change-password', { data: password });
}

export async function createUser(user) {
  return await request.post('/users', { data: user });
}

export async function queryUserByPageable(params) {
  return await request.get('/users/pageable', { params });
}

export async function updateUser(user) {
  return await request.put('/users', { data: user });
}

export async function deleteUser(id) {
  return await request.delete(`/users/${id}`);
}

export async function enableUser(id) {
  return await request.put(`/users/enable/${id}`);
}

export async function disableUser(id) {
  return await request.put(`/users/disable/${id}`);
}
