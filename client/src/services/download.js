import request from '@/utils/request';

export async function localDownload(url) {
  return await request.get(url);
}
