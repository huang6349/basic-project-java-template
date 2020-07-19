import * as React from 'react';

import { useDefaultPage } from '@/hooks';

const IndexPage = ({ location: { pathname } }) => {
  useDefaultPage(pathname);

  return <React.Fragment />;
};

export default IndexPage;
