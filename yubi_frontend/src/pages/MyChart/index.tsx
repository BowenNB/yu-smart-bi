import { listMyChartByPageUsingPost } from '@/services/yubi/chartController';
import { Avatar, List, message } from 'antd';
import React, { useEffect, useState } from 'react';
import ReactECharts from 'echarts-for-react';
import { useModel } from '@/.umi/exports';

/**
 * 我的图表页面
 * @constructor
 */
const MyChartPage: React.FC = () => {
  const initSearchParams = {
    // 默认第一页
    current: 1,
    // 默认每页4条
    pageSize: 4,
  };

  const [searchParams, setSearchParams] = useState<API.ChartQueryRequest>({ ...initSearchParams });
  // 从全局状态中获取当前登录用户的信息
  const {initialState} =  useModel('@@initialState');
  const {currentUser} = initialState ?? {};
  const [chartList, setChartList] = useState<API.Chart[]>();
  const [total, setTotal] = useState<number>(0);
  // 加载状态，用来控制页面是否加载，默认正在加载
  const [loading, setLoading] = useState<boolean>(true);

  const loadData = async () => {
    // 获取数据中，还在加载中，把loading设置为true
    setLoading(true);
    try {
      const res = await listMyChartByPageUsingPost(searchParams);

      if (res.data) {
        setChartList(res.data.records ?? []);
        setTotal(res.data.total ?? 0);
        // 有些有标题，有些没有，直接把标题全部去掉
        if(res.data.records){
          res.data.records.forEach(data => {
            // 要把后端返回的图表字符串改为对象数组,如果后端返回空字符串，就返回'{}'
            const chartOption  = JSON.parse(data.genChart ?? '{}');
            // 把标题设为undefined
            chartOption.title = undefined;
            // 然后把修改后的数据转换为json设置回去
            data.genChart = JSON.stringify(chartOption);
          })
      } else {
        message.error('获取我的图表失败');
      }
    }} catch (e: any) {
      message.error('获取我的图表失败,' + e.message);
    }
    // 获取数据完成，把loading设置为false
    setLoading(false);
  };
  


  useEffect(() => {
    loadData();
  }, [searchParams]);

  return (
    <div className="my-chart-page">
      <List
        itemLayout="vertical"
        size="large"
        pagination={{
          onChange: (page) => {
            console.log(page);
          },
          pageSize: 3,
        }}
        dataSource={chartList}
        footer={
          <div>
            <b>ant design</b> footer part
          </div>
        }
        renderItem={(item) => (
          <List.Item
            key={item.id}
            // 在extra展示图表默认没有width(宽度)，需要自己设置，无法适配
            // extra={
            // }
            >
            <List.Item.Meta
              avatar={<Avatar src={'https://randomuser.me/api/portraits/men/34.jpg'} />}
              title={item.name}
              description={item.chartType ? '图表类型' + item.chartType : undefined}
              />
            {'分析目标' + item.goal}	
             {/* 
              把在智能分析页的图表展示复制粘贴到此处;
              要把后端返回的图表字符串改为对象数组,如果后端返回空字符串，就返回'{}' 
            */}
            <ReactECharts option={JSON.parse(item.genChart ?? '{}')} />
          </List.Item>
        )}
        />
      总数：{total}
    </div>
  );
};
export default MyChartPage;
