import request from '@/utils/request';

export async function createDict(dict = {}) {
  return await request.post('/dict', { data: dict });
}

export async function queryDict(params = {}) {
  return await request.get('/dict', { params });
}

export async function queryDictByPageable(params = {}) {
  return await request.get('/dict/pageable', { params });
}

export async function queryDictToTree(params = {}) {
  return await request.get('/dict/tree', { params });
}

export async function queryDictToChildren(code = {}) {
  return await request.get(`/dict/children/${code}`);
}

export async function updateDict(dict = {}) {
  return await request.put('/dict', { data: dict });
}

export async function deleteDict(id) {
  return await request.delete(`/dict/${id}`);
}
