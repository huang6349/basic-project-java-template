(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[1],{A0qh:function(e,n,t){e.exports={layout:"layout___2MlGu",header:"header___2Xz_G"}},Dhku:function(e,n,t){e.exports={layout:"layout___3YQiT"}},aArQ:function(e,n,t){"use strict";t.r(n);var a=t("p0pE"),r=t.n(a),o=t("Y/ft"),l=t.n(o),u=t("d6i3"),c=t.n(u),i=t("1l/V"),m=t.n(i),f=t("aBYM"),s=t.n(f),p=t("q1tI"),d=t("Hx5s"),h=t("mOP9"),g=t.n(h),y=t("MuoO"),v=t("usdK"),E=t.n(v),w=t("bALw"),k=t.n(w),b=t("oAJy"),x=t.n(b),_=(t("lUTK"),t("BvKs")),P=(t("17x9"),t("Q9mQ"),t("diRs")),T=(t("Telt"),t("Tckk")),A=t("E+oP"),D=t.n(A),M=(t("Pwec"),t("CtXQ")),R=(t("2qtc"),t("kLXV"));function q(e){var n=e.menus,t=e.onSelect,a=e.onPoweroff,r=n.filter(function(e){var n=e.hideMenu;return!n});function o(e){var n=e.key;"poweroff"===n?R["a"].confirm({title:"\u9000\u51fa\u63d0\u793a",content:"\u4f60\u786e\u5b9a\u8981\u8fdb\u884c\u9000\u51fa\u64cd\u4f5c\u5417",onOk:function(){"function"===typeof a&&a()}}):"function"===typeof t&&t(e)}return p["createElement"](_["a"],{selectedKeys:[],onClick:o},r.map(function(e){var n=e.key,t=e.icon,a=e.title;return p["createElement"](_["a"].Item,{key:n},p["createElement"](M["a"],{type:t})," ",a)}),r.length>0?p["createElement"](_["a"].Divider,null):null,p["createElement"](_["a"].Item,{key:"poweroff"},p["createElement"](M["a"],{type:"poweroff"})," \u9000\u51fa\u767b\u5f55"))}q.defaultProps={menus:[]};var C=t("xnx0"),N=t.n(C);function F(e){var n=e.username,t=e.children,a=p["Children"].map(t,function(e){return e.type===q?p["cloneElement"](e):null});return p["createElement"](P["a"],{arrowPointAtCenter:!0,overlayClassName:N.a["popover"],placement:"bottomRight",trigger:"click",content:D()(a)?p["createElement"](q,null):a,title:"\u5f53\u524d\u7528\u6237\uff1a".concat(n)},p["createElement"]("div",{className:N.a["layout"]},p["createElement"](T["a"],{icon:"user"})))}F.defaultProps={username:"\u65e0"},F.AvatarMenu=q;var I=t("Dhku"),J=t.n(I);function K(e){var n=e.username,t=e.avatarMenus,a=e.onSelect,r=e.onPoweroff;return p["createElement"](_["a"],{className:J.a["layout"],mode:"horizontal",selectedKeys:[]},p["createElement"](_["a"].Item,{className:J.a["user"],key:"avatar"},p["createElement"](F,{username:n},p["createElement"](F.AvatarMenu,{menus:t,onSelect:a,onPoweroff:r}))))}K.defaultProps={};var O=t("Jn7e"),Q=t("A0qh"),S=t.n(Q),X=function(e){var n=e.global,t=e.location,a=e.dispatch,o=e.children,u=s()(m()(c.a.mark(function e(){return c.a.wrap(function(e){while(1)switch(e.prev=e.next){case 0:return e.next=2,x.a.getItem(O["b"]["name"]);case 2:return e.abrupt("return",!!e.sent);case 3:case"end":return e.stop()}},e)})),[]),i=u.value,f=u.loading;p["useEffect"](function(){f||a({type:"global/keep",payload:{hasToken:i}})},[i]);var h=n.isLogin,y=n.username,v=n.menuData;if(p["useEffect"](function(){!f&&h&&null!==h&&a({type:"global/fetchUser"})},[h]),f||null===h)return p["createElement"](p["Fragment"],null,"\u7528\u6237\u9a8c\u8bc1\u8eab\u4efd\u4e2d...");var w=t.pathname;if(h&&k()("/login").exec(w))return setTimeout(function(){E.a.replace({pathname:"/",query:{k:(new Date).getTime()}})},0),p["createElement"](p["Fragment"],null);if(!h&&!k()("/login").exec(w))return setTimeout(function(){E.a.replace({pathname:"/login",query:{k:(new Date).getTime()}})},0),p["createElement"](p["Fragment"],null);if(k()("/login").exec(w))return p["createElement"](p["Fragment"],null,o);function b(){a({type:"global/logout"})}return p["createElement"](d["b"],{className:S.a["layout"],location:t,title:"basic",logo:!1,menuHeaderRender:function(){return p["createElement"]("div",{className:S.a["header"]})},layout:"sidemenu",contentWidth:"Fluid",navTheme:"light",fixedHeader:!0,fixSiderbar:!1,collapsedButtonRender:!1,rightContentRender:function(){return p["createElement"](K,{username:y,onPoweroff:b})},menuItemRender:function(e,n){return e["children"]||!e["path"]?n:p["createElement"](g.a,{to:{pathname:e["path"],query:{k:(new Date).getTime()}}},n)},breadcrumbRender:function(e){return e.map(function(e){var n=e.path,t=l()(e,["path"]);return r()({path:"#".concat(n)},t)})},menuDataRender:function(){return v}},p["createElement"](p["Fragment"],null,o))};function B(e){var n=e.global,t=e.loading.global;return{global:n,loading:t}}n["default"]=Object(y["connect"])(B)(X)},xnx0:function(e,n,t){e.exports={layout:"layout___doNMa",popover:"popover___2OCJX"}}}]);