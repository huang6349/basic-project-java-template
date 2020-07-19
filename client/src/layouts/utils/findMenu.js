import isEmpty from 'lodash/isEmpty';
import head from 'lodash/head';
import pathsToRegexp from './pathsToRegexp';

export default function(menuData = [], pathname) {
  return head((deepQuery(menuData, pathname) || {})['children']);
}

function deepQuery(menuData = [], pathname) {
  let stark = [];
  stark = stark.concat(menuData);
  while (stark['length']) {
    const temp = stark.shift();
    if (pathsToRegexp(['/', temp['path']], pathname)) {
      return temp;
    }
    if (!isEmpty(temp['children'])) {
      stark = temp['children'].concat(stark);
    }
  }
}
