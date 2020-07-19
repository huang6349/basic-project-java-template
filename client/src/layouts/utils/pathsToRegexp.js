import find from 'lodash/find';
import pathToRegexp from 'path-to-regexp';

export default function(paths = [], pathname) {
  return find(paths, (path) => pathToRegexp(path).exec(pathname));
}
