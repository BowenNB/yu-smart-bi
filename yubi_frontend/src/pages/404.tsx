import { history } from '@umijs/max';
import { Button, Card, Result } from 'antd';
import React from 'react';
const NoFoundPage: React.FC = () => (
  <Card variant="borderless">
    <Result
      status="404"
      title="404"
      subTitle={'pages.404.subTitle'}
      extra={
        <Button type="primary" onClick={() => history.push('/')}>
          {'pages.404.buttonText'}
        </Button>
      }
    />
  </Card>
);
export default NoFoundPage;
