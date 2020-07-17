import localforage from 'localforage';

import account from '@/services/account';

export default async function() {
  const hasToken = !!(await localforage.getItem(TOKEN_NAME));

  let initialState = { isLogin: hasToken };

  if (hasToken) {
    initialState = { ...initialState, ...(await account()) };
  }

  return initialState;
}
