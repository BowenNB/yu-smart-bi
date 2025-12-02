import { listChartByPageUsingPost} from '@/services/yubi/chartController';
import { UploadOutlined } from '@ant-design/icons';
import { useModel } from '@umijs/max';
import { Button, Form, Input, Select, Space, Upload, message } from 'antd';
import TextArea from 'antd/es/input/TextArea';
import React, { useEffect, useState } from 'react';
import { genChartByAiUsingPost } from '@/services/yubi/chartController';
import ReactECharts from 'echarts-for-react';

/**
 * 添加图表页面
 */

const AddChart: React.FC = () => {

    useEffect(() => {
    listChartByPageUsingPost({}).then((res) => {
      console.error('res', res);
    });
  });

  // 定义状态，用来接收后端的返回值，让它实时展示在页面上
  const [chart, setChart] = useState<API.BiResponseVO>();
  const [option, setOption] = useState<any>();
  // 提交中的状态，默认未提交
  const [submitting, setSubmitting] = useState<boolean>(false);

  /**
   * 提交
   * @param values
   */
    const onFinish = async (values: any) => {
      // 如果已经是提交中的状态(还在加载)，直接返回，避免重复提交
    if (submitting) {
      return;
    }
    // 当开始提交，把submitting设置为true
    setSubmitting(true);

      // 对接后端，上传数据
      const params = {
        ...values,
        file: undefined,
      };
      try {
        // 需要取到上传的原始数据file→file→originFileObj(原始数据)
        const res = await genChartByAiUsingPost(params, {}, values.file.file.originFileObj);
        // 正常情况下，如果没有返回值就分析失败，有，就分析成功
        if (!res?.data) {
          message.error('分析失败');
        } else {
          message.success('分析成功');  
          // 解析成对象，为空则设为空字符串
          const chartOption = JSON.parse(res.data.genChart ?? '');
          // 如果为空，则抛出异常，并提示'图表代码解析错误'
          if (!chartOption) {
            throw new Error('图表代码解析错误')
          // 如果成功
          } else {
            // 从后端得到响应结果之后，把响应结果设置到图表状态里
            setChart(res.data);
            setOption(chartOption);
          }
        }  
      // 异常情况下，提示分析失败+具体失败原因
      } catch (e: any) {
        message.error('分析失败,' + e.message);
      }
      // 当结束提交，把submitting设置为false
      setSubmitting(false);
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
  {/* 前端表单的name，对应后端接口请求参数里的字段，
  此处name对应后端分析目标goal,label是左侧的提示文本，
  rules=....是必填项提示*/}
  <Form.Item name="goal" label="分析目标" rules={[{ required: true, message: '请输入分析目标!' }]}>
      {/* placeholder文本框内的提示语 */}
      <TextArea placeholder="请输入你的分析需求，比如：分析网站用户的增长情况"/>
  </Form.Item>

  {/* 还要输入图表名称 */}
  <Form.Item name="name" label="图表名称">
      <Input placeholder="请输入图表名称" />
  </Form.Item>

  {/* 图表类型是非必填，所以不做校验 */}
  <Form.Item
    name="selchartTypeect"
    label="图表类型"
    >
    <Select
    options={[
      { value: '折线图', label: '折线图' },
      { value: '柱状图', label: '柱状图' },
      { value: '堆叠图', label: '堆叠图' },
      { value: '饼图', label: '饼图' },
      { value: '雷达图', label: '雷达图' },
    ]}
    />
  </Form.Item>

  {/* 文件上传 */}
  <Form.Item
      name="file"
      label="原始数据"
    >
      {/* action:当你把文件上传之后，他会把文件上传至哪个接口。
      这里肯定是调用自己的后端，先不用这个 */}
      <Upload name="file">
        <Button icon={<UploadOutlined />}>上传 CSV 文件</Button>
      </Upload>
    </Form.Item>
    
    <Form.Item wrapperCol={{ span: 12, offset: 6 }}>
      <Space>
        <Button type="primary" htmlType="submit">
          提交
        </Button>
        <Button htmlType="reset">重置</Button>
      </Space>
    </Form.Item>
  </Form>

  <div>
    分析结论：{chart?.genResult}
  </div>
  <div>
    生成图表：
    {/* 如果它存在，才渲染这个组件 */}
    {
      // 后端返回的代码是字符串，不是对象，用JSON.parse解析成对象
      option && <ReactECharts option={option} />
    }
  </div>
    </div>
  );
};
export default AddChart;
