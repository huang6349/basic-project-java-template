(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[6],{sYaC:function(e,t,a){"use strict";a.r(t);var n=a("p0pE"),r=a.n(n),i=a("d6i3"),s=a.n(i),c=a("bALw"),p=a.n(c),u=a("1l/V"),o=a.n(u),l=a("t3Un");function f(){return d.apply(this,arguments)}function d(){return d=o()(s.a.mark(function e(){var t,a=arguments;return s.a.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return t=a.length>0&&void 0!==a[0]?a[0]:{},e.next=3,l["a"].get("/file/pageable",{params:t});case 3:return e.abrupt("return",e.sent);case 4:case"end":return e.stop()}},e)})),d.apply(this,arguments)}t["default"]={state:{current:1,pageSize:10,total:0,list:[]},subscriptions:{setup:function(e){var t=e.dispatch,a=e.history;a.listen(function(e){var a=e.pathname,n=p()("/data/file").exec(a);n&&t({type:"fetchFile",payload:{init:!0}})})}},effects:{fetchFile:s.a.mark(function e(t,a){var n,r,i,c,p,u,o,l,d,h,w,v,g,y,x,b,m,z;return s.a.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return n=t.payload,r=a.select,i=a.call,c=a.put,e.next=4,r(function(e){var t=e.file;return t});case 4:return p=e.sent,u=p.current,o=p.pageSize,l=p.search,d=n.current,h=n.pageSize,w=n.search,v=n.init,e.next=11,i(f,{page:((v?u:d)||1)-1,size:(v?o:h)||10,name:v?l:w});case 11:return g=e.sent,y=g.data,x=g.params,x=void 0===x?{}:x,b=x.page,m=x.size,z=x.total,e.next=18,c({type:"updateState",payload:{current:b+1,pageSize:m,total:z,list:y,search:v?l:w}});case 18:case"end":return e.stop()}},e)})},reducers:{updateState:function(e,t){var a=t.payload;return r()({},e,a)}}}}}]);