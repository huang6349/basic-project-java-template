import * as React from 'react';
import { useModel } from 'umi';
import localforage from 'localforage';

const IndexPage = () => {
  const { refresh } = useModel('@@initialState');

  function handleClick(e) {
    e.preventDefault();
    localforage.removeItem(TOKEN_NAME).then(() => refresh());
  }

  return (
    <React.Fragment>
      <span>Hello, Basic Project Java Template, </span>
      <a href="###" onClick={handleClick}>
        <span>注销</span>
      </a>
    </React.Fragment>
  );
};

export default IndexPage;
