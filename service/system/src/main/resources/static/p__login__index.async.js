(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[3],{gMHI:function(e,a,t){"use strict";t.r(a);var n=t("q1tI"),l=t("MuoO"),r=(t("+L6B"),t("2/Rp")),o=(t("y8nQ"),t("Vl3Y")),i=(t("5NDa"),t("5rEg")),c=(t("Pwec"),t("CtXQ"));function u(e){var a=e.form,t=e.loading,l=e.onSubmit,u=a.getFieldDecorator;function s(e){e.preventDefault(),a.validateFields(function(e,a){e||"function"===typeof l&&l(a)})}return n["createElement"](o["a"],{hideRequiredMark:!0,layout:"horizontal",onSubmit:s,style:{marginTop:"24px"}},n["createElement"](o["a"].Item,null,u("username",{rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u60a8\u7684\u8d26\u53f7"}]})(n["createElement"](i["a"],{placeholder:"\u8bf7\u8f93\u5165\u8d26\u53f7",prefix:n["createElement"](c["a"],{type:"user",style:{color:"rgba(0, 0, 0, 0.25)"}}),size:"large"}))),n["createElement"](o["a"].Item,null,u("password",{rules:[{required:!0,message:"\u8bf7\u8f93\u5165\u60a8\u7684\u5bc6\u7801"}]})(n["createElement"](i["a"].Password,{placeholder:"\u8bf7\u8f93\u5165\u5bc6\u7801",prefix:n["createElement"](c["a"],{type:"lock",style:{color:"rgba(0, 0, 0, 0.25)"}}),size:"large"}))),n["createElement"](o["a"].Item,null,n["createElement"](r["a"],{type:"primary",htmlType:"submit",loading:t,size:"large",block:!0},"\u767b\u5f55")))}var s=o["a"].create()(u),m=t("i9FS"),d=t.n(m),p=function(e){var a=e.loading,t=e.dispatch;function l(e){t({type:"global/login",payload:e})}return n["createElement"]("section",{className:d.a["layout"]},n["createElement"]("div",{className:d.a["main"],style:{width:365}},n["createElement"](s,{loading:a,onSubmit:l})))};function g(e){var a=e.loading;return{loading:a["global"]}}a["default"]=Object(l["connect"])(g)(p)},i9FS:function(e,a,t){e.exports={layout:"layout___2HsEz",main:"main___2ydhE"}}}]);