import { listChartByPageUsingPost } from '@/services/yubi/chartController';
import { UploadOutlined } from '@ant-design/icons';
import { useModel } from '@umijs/max';
import { Button, Form, Select, Space, Upload } from 'antd';
const { Option } = Select;
import TextArea from 'antd/es/input/TextArea';
import React, { useEffect, useState } from 'react';

const Login: React.FC = () => {
  const [type, setType] = useState<string>('account');
  const { setInitialState } = useModel('@@initialState');

  useEffect(() => {
    listChartByPageUsingPost({}).then((res) => {
      console.error('res', res);
    });
  });

  const onFinish = (values: any) => {
    console.log('Received values of form: ', values);
  };

  return (
    // 把页面内容指定一个类名add-chart
    <div className="add-chart">
      <Form
        // 表单名称改为addChart
        name="addChart"
        onFinish={onFinish}
        // 初始化数据啥都不填，为空
        initialValues={{  }}
  >
  <Form.Item name="rate" label="Rate">
      <TextArea />
  </Form.Item>
  
  <Form.Item
    name="select"
    label="Select"
    hasFeedback
    rules={[{ required: true, message: 'Please select your country!' }]}
    >
    <Select placeholder="Please select a country">
      <Option value="china">China</Option>
      <Option value="usa">U.S.A</Option>
    </Select>
  </Form.Item>
  
  <Form.Item
      name="upload"
      label="Upload"
      valuePropName="fileList"
      extra="longgggggggggggggggggggggggggggggggggg"
    >
      <Upload name="logo" action="/upload.do" listType="picture">
        <Button icon={<UploadOutlined />}>Click to upload</Button>
      </Upload>
    </Form.Item>

    
    <Form.Item wrapperCol={{ span: 12, offset: 6 }}>
      <Space>
        <Button type="primary" htmlType="submit">
          Submit
        </Button>
        <Button htmlType="reset">reset</Button>
      </Space>
    </Form.Item>
  </Form>
    </div>
  );
};
export default Login;
