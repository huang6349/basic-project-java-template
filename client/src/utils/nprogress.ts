import NProgress from 'nprogress';

NProgress.configure({
  showSpinner: !1,
});

let isNProgress = !1;

export function start() {
  if (isNProgress) return;
  isNProgress = !0;
  NProgress.start();
  NProgress.inc();
}

export function done() {
  isNProgress = !1;
  NProgress.done();
}

export default { start, done };
