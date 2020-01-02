import * as React from 'react';
import { connect } from 'dva';
import { Form } from './components';
import styles from './index.css';

const IndexPage = ({ loading, dispatch }) => {
  function handleSubmit(data) {
    dispatch({ type: 'global/login', payload: data });
  }

  return (
    <section className={styles['layout']}>
      <div className={styles['main']} style={{ width: 365 }}>
        <Form loading={loading} onSubmit={handleSubmit} />
      </div>
    </section>
  );
};

function mapStateToProps({ loading }) {
  return { loading: loading['global'] };
}

export default connect(mapStateToProps)(IndexPage);
