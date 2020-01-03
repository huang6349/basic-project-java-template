import request from '@/utils/request';

export async function createAuthority(authority) {
  return await request.post('/authority', { data: authority });
}

export async function queryAuthority(params) {
  return await request.get('/authority', { params });
}

export async function queryAuthorityByPageable(params) {
  return await request.get('/authority/pageable', { params });
}

export async function updateAuthority(authority) {
  return await request.put('/authority', { data: authority });
}

export async function deleteAuthority(id) {
  return await request.delete(`/authority/${id}`);
}
