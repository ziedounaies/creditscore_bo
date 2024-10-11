import {useAppDispatch, useAppSelector} from "app/config/store";
import {hasAnyAuthority} from "app/shared/auth/private-route";
import {AUTHORITIES} from "app/config/constants";
import React, {ReactNode, useEffect,useState} from "react";
import {Card, Col, Divider, message, Rate, Row, Slider, Statistic, Typography} from "antd";
import { ResponsiveLine } from '@nivo/line'
import { ResponsivePie } from '@nivo/pie'
import { ResponsiveRadar } from '@nivo/radar'
import {
  ArrowDownOutlined,
  ArrowUpOutlined, CreditCardOutlined,
  ExclamationCircleOutlined,
  FileTextOutlined, ProductOutlined,
  UserOutlined
} from '@ant-design/icons';
import {getEntities as getEntitiesClients,getEntities as getEntitiesMerchants} from "app/modules/dashboard/member-user/member-user.reducer";
import {getEntities as getEntitiesInvoices } from "app/modules/dashboard/invoice/invoice.reducer";
import {getEntities as getEntitiesClaims} from "app/modules/dashboard/claim/claim.reducer";
import {getEntities as getEntitiesProducts} from "app/modules/dashboard/product/product.reducer";
import {getEntities as getEntitiesPayments} from "app/modules/dashboard/payment/payment.reducer";


const { Title, Text } = Typography;
const data = [
  {
    "id": "Commercants",
    "color": "hsl(116, 70%, 50%)",
    "data": [
      {
        "x": "20/06/2024",
        "y": 298
      },
      {
        "x": "helicopter",
        "y": 297
      },
      {
        "x": "boat",
        "y": 164
      },
      {
        "x": "train",
        "y": 19
      },
      {
        "x": "subway",
        "y": 205
      },
      {
        "x": "bus",
        "y": 58
      },
      {
        "x": "car",
        "y": 50
      },
      {
        "x": "moto",
        "y": 262
      },
      {
        "x": "bicycle",
        "y": 256
      },
      {
        "x": "horse",
        "y": 199
      },
      {
        "x": "skateboard",
        "y": 77
      },
      {
        "x": "others",
        "y": 211
      }
    ]
  },
  {
    "id": "Clients",
    "color": "hsl(316, 70%, 50%)",
    "data": [
      {
        "x": "20/06/2024",
        "y": 42
      },
      {
        "x": "helicopter",
        "y": 30
      },
      {
        "x": "boat",
        "y": 77
      },
      {
        "x": "train",
        "y": 145
      },
      {
        "x": "subway",
        "y": 220
      },
      {
        "x": "bus",
        "y": 263
      },
      {
        "x": "car",
        "y": 204
      },
      {
        "x": "moto",
        "y": 72
      },
      {
        "x": "bicycle",
        "y": 154
      },
      {
        "x": "horse",
        "y": 29
      },
      {
        "x": "skateboard",
        "y": 25
      },
      {
        "x": "others",
        "y": 111
      }
    ]
  },
];
const dataMerchant = [
  {
    "id": "En cours",
    "label": "En cours",
    "value": 341,
    "color": "hsl(141, 70%, 50%)"
  },
  {
    "id": "Impayé",
    "label": "Impayé",
    "value": 398,
    "color": "hsl(102, 70%, 50%)"
  },
  {
    "id": "Payé",
    "label": "Payé",
    "value": 214,
    "color": "hsl(38, 70%, 50%)"
  },
  {
    "id": "En attente",
    "label": "En attente",
    "value": 166,
    "color": "hsl(67, 70%, 50%)"
  },
];
const dataProducts = [
  {
    "taste": "reclamtion en anttente ",

    "Products": 98,
    "syrah": 74
  },
  {
    "taste": "reclamation traité",
    "Products": 107,
  },

];


export const Overview = () => {
  const account = useAppSelector(state => state.authentication.account);
  const dispatch = useAppDispatch();
  const totalItemsClients = useAppSelector(state => state.memberUser.totalItems);
  const totalItemsInvoices = useAppSelector(state => state.invoice.totalItems);
  const totalItemsClaims = useAppSelector(state => state.claim.totalItems);
  const totalItemsProducts = useAppSelector(state => state.product.totalItems);
  const totalItemsPayments = useAppSelector(state => state.payment.totalItems);

  const isAuthenticated = useAppSelector(state => state.authentication.isAuthenticated);
  const isAdmin = useAppSelector(state => hasAnyAuthority(state.authentication.account.authorities, [AUTHORITIES.ADMIN]));
  const ribbonEnv = useAppSelector(state => state.applicationProfile.ribbonEnv);
  const isInProduction = useAppSelector(state => state.applicationProfile.inProduction);
  const isOpenAPIEnabled = useAppSelector(state => state.applicationProfile.isOpenAPIEnabled);
  // @ts-ignore


  const [showAlert, setShowAlert] = useState(false);

  useEffect(() => {
    // Show the alert when the account is logged in
    if (account?.login) {
      setShowAlert(true);

      // Set a timer to hide the alert after 5 seconds
      const timer = setTimeout(() => {
        setShowAlert(false);
      }, 5000);

      // Clear the timer when the component unmounts or when the account changes
      return () => clearTimeout(timer);
    }
  }, [account]);

  useEffect(() => {
    dispatch(
      getEntitiesClients({
        query:`role.equals=CUSTOMER`
      })
    );

    // dispatch(
    //   getEntitiesMerchants({
    //     query:`role.equals=COMMERCANT`
    //   })
    // );
    dispatch(
      getEntitiesInvoices({
        query:`status.in=IN_PROGRESS,PENDING`
      }),
    );

    dispatch(
      getEntitiesClaims({
        query:`status.in=IN_PROGRESS,PENDING`
      }),
    );

    dispatch(
      getEntitiesProducts({

      }),
    );

    dispatch(
      getEntitiesPayments({
        query:`status.equals=REJECTED`
      }),
    );
  }, []);


  const [open, setOpen] = React.useState(false);
  // useEffect(() => {
  //   if (account?.login) {
  //     message.info(`Vous êtes connecté en tant que "${account.login}".`);
  //   }
  // }, [account]); // Trigger effect when account changes
  const toggleDrawer = (newOpen: boolean) => () => {
    setOpen(newOpen);
  };

  const { Title } = Typography;

  const [value, setValue] = React.useState<number | null>(3);
  return (

    <div >

      <Card style={{
        borderRadius: 10,
        height: '12%',
        width: '100%',
        //backgroundColor: '#F2FFE9',

        boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)'
      }}>

        <Title  level={2}>Overview</Title>
      </Card>

      {/* STAT ADMIN*/}

      {  hasAnyAuthority(account.authorities, [AUTHORITIES.ADMIN]) && <div style={{marginTop: 20, marginBottom: 10,}}>
        <Row gutter={16}>
          <Col span={6}>
            <Card bordered={false}>
              <Statistic
                title="Nombre de clients"
                value={totalItemsClients}
                precision={0}
                valueStyle={{color: 'black'}}
                prefix={<UserOutlined/>}
              />
            </Card>
          </Col>
          <Col span={6}>
            <Card bordered={false}>
              <Statistic
                title="Nombre de commercants"
                value={11}
                precision={0}
                valueStyle={{color: 'black'}}
                prefix={<CreditCardOutlined/>}
              />
            </Card>
          </Col>
          <Col span={6}>
            <Card bordered={false}>
              <Statistic
                title="Nombre de factures en cours"
                value={totalItemsInvoices}
                precision={0}
                valueStyle={{color: "#FFA500"}}
                prefix={<FileTextOutlined/>}
              />
            </Card>
          </Col>
          <Col span={6}>
            <Card bordered={false}>
              <Statistic
                title="Nombre de réclamations en cours"
                value={totalItemsClaims}
                precision={0}
                valueStyle={{color: "#FFA500"}}
                prefix={<ExclamationCircleOutlined/>}
              />
            </Card>
          </Col>
        </Row>
        <div
          style={{display: 'flex', justifyContent: 'center', alignItems: 'center', marginTop: 20, marginBottom: 10,}}>
          <div style={{
            borderRadius: 10,
            height: 330,
            width: '100%',
            backgroundColor: 'white',
            boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
            marginRight: 10
          }}>

            <ResponsiveLine
              data={data}
              margin={{top: 50, right: 110, bottom: 50, left: 60}}
              xScale={{type: 'point'}}
              yScale={{
                type: 'linear',
                min: 'auto',
                max: 'auto',
                stacked: true,
                reverse: false
              }}
              yFormat=" >-.2f"
              axisTop={null}
              axisRight={null}
              axisBottom={{
                tickSize: 5,
                tickPadding: 5,
                tickRotation: 0,
                legend: 'transportation',
                legendOffset: 36,
                legendPosition: 'middle',
                truncateTickAt: 0
              }}
              axisLeft={{
                tickSize: 5,
                tickPadding: 5,
                tickRotation: 0,
                legend: 'count',
                legendOffset: -40,
                legendPosition: 'middle',
                truncateTickAt: 0
              }}
              pointSize={10}
              pointColor={{theme: 'background'}}
              pointBorderWidth={2}
              pointBorderColor={{from: 'serieColor'}}
              pointLabel="data.yFormatted"
              pointLabelYOffset={-12}
              enableTouchCrosshair={true}
              useMesh={true}
              legends={[
                {
                  anchor: 'bottom-right',
                  direction: 'column',
                  justify: false,
                  translateX: 100,
                  translateY: 0,
                  itemsSpacing: 0,
                  itemDirection: 'left-to-right',
                  itemWidth: 80,
                  itemHeight: 20,
                  itemOpacity: 0.75,
                  symbolSize: 12,
                  symbolShape: 'circle',
                  symbolBorderColor: 'rgba(0, 0, 0, .5)',
                  effects: [
                    {
                      on: 'hover',
                      style: {
                        itemBackground: 'rgba(0, 0, 0, .03)',
                        itemOpacity: 1
                      }
                    }
                  ]
                }
              ]}
            />


          </div>

        </div>
      </div>}

      {  hasAnyAuthority(account.authorities, [AUTHORITIES.MERCHANT]) && <div style={{marginTop: 20, marginBottom: 10,}}>
        <Row gutter={16}>
          <Col span={6}>
            <Card bordered={false}>
              <Statistic
                title="Nombre de clients"
                value={totalItemsClients}
                precision={0}
                valueStyle={{color: 'black'}}
                prefix={<UserOutlined/>}
              />
            </Card>
          </Col>
          <Col span={6}>
            <Card bordered={false}>
              <Statistic
                title="Nombre de produits"
                value={totalItemsProducts}
                precision={0}
                valueStyle={{color: 'black'}}
                prefix={<ProductOutlined/>}
              />
            </Card>
          </Col>

          <Col span={12}>
            <Card bordered={false}>
              <Statistic
                title="Nombre de paiements impayés"
                value={totalItemsPayments}
                precision={0}
                valueStyle={{color: '#cf1322'}}
                prefix={<CreditCardOutlined/>}
              />
            </Card>
          </Col>
        </Row>
        <div
          style={{display: 'flex', justifyContent: 'center', alignItems: 'center', marginTop: 20, marginBottom: 10,}}>


          <div style={{
            borderRadius: 10,
            height: 330,
            width: '50%',
            backgroundColor: 'white',
            boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
            marginRight: 10
          }}>

            <ResponsiveRadar
              data={dataProducts}
              keys={[ 'Products']}
              indexBy="taste"
              valueFormat=">-.2f"
              margin={{top: 70, right: 80, bottom: 40, left: 80}}
              borderColor={{from: 'color'}}
              gridLabelOffset={36}
              dotSize={10}
              dotColor={{theme: 'background'}}
              dotBorderWidth={2}
              colors={{scheme: 'nivo'}}
              blendMode="multiply"
              motionConfig="wobbly"
              legends={[
                {
                  anchor: 'top-left',
                  direction: 'column',
                  translateX: -50,
                  translateY: -40,
                  itemWidth: 80,
                  itemHeight: 20,
                  itemTextColor: '#999',
                  symbolSize: 12,
                  symbolShape: 'circle',
                  effects: [
                    {
                      on: 'hover',
                      style: {
                        itemTextColor: '#000'
                      }
                    }
                  ]
                }
              ]}
            />


          </div>

          <div style={{
            borderRadius: 10,
            height: 330,
            width: '50%',
            backgroundColor: 'white',
            boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
            marginRight: 10
          }}>

            <ResponsivePie
              data={dataMerchant}
              margin={{top: 40, right: 80, bottom: 80, left: 80}}
              innerRadius={0.5}
              padAngle={0.7}
              cornerRadius={3}
              activeOuterRadiusOffset={8}
              borderWidth={1}
              borderColor={{
                from: 'color',
                modifiers: [
                  [
                    'darker',
                    0.2
                  ]
                ]
              }}
              arcLinkLabelsSkipAngle={10}
              arcLinkLabelsTextColor="#333333"
              arcLinkLabelsThickness={2}
              arcLinkLabelsColor={{from: 'color'}}
              arcLabelsSkipAngle={10}
              arcLabelsTextColor={{
                from: 'color',
                modifiers: [
                  [
                    'darker',
                    2
                  ]
                ]
              }}
              defs={[
                {
                  id: 'dots',
                  type: 'patternDots',
                  background: 'inherit',
                  color: 'rgba(255, 255, 255, 0.3)',
                  size: 4,
                  padding: 1,
                  stagger: true
                },
                {
                  id: 'lines',
                  type: 'patternLines',
                  background: 'inherit',
                  color: 'rgba(255, 255, 255, 0.3)',
                  rotation: -45,
                  lineWidth: 6,
                  spacing: 10
                }
              ]}
              fill={[
                {
                  match: {
                    id: 'ruby'
                  },
                  id: 'dots'
                },
                {
                  match: {
                    id: 'c'
                  },
                  id: 'dots'
                },
                {
                  match: {
                    id: 'go'
                  },
                  id: 'dots'
                },
                {
                  match: {
                    id: 'python'
                  },
                  id: 'dots'
                },
                {
                  match: {
                    id: 'scala'
                  },
                  id: 'lines'
                },
                {
                  match: {
                    id: 'lisp'
                  },
                  id: 'lines'
                },
                {
                  match: {
                    id: 'elixir'
                  },
                  id: 'lines'
                },
                {
                  match: {
                    id: 'javascript'
                  },
                  id: 'lines'
                }
              ]}
              legends={[
                {
                  anchor: 'bottom',
                  direction: 'row',
                  justify: false,
                  translateX: 0,
                  translateY: 56,
                  itemsSpacing: 0,
                  itemWidth: 100,
                  itemHeight: 18,
                  itemTextColor: '#999',
                  itemDirection: 'left-to-right',
                  itemOpacity: 1,
                  symbolSize: 18,
                  symbolShape: 'circle',
                  effects: [
                    {
                      on: 'hover',
                      style: {
                        itemTextColor: '#000'
                      }
                    }
                  ]
                }
              ]}
            />


          </div>


        </div>
      </div>}

      {  hasAnyAuthority(account.authorities, [AUTHORITIES.USER]) && <div style={{marginTop: 20, marginBottom: 10,}}>
        <Row gutter={16}>
          <Col span={24}>
            <Card bordered={false}>
              <Text> score de credit</Text>
              <div style={{fontSize: '50px', color: '#ff6347', fontWeight: 'bold', fontFamily: 'Arial, sans-serif'}}>
                665
              </div>

            </Card>
          </Col>


        </Row>
        <div
          style={{display: 'flex', justifyContent: 'center', alignItems: 'center', marginTop: 20, marginBottom: 10,}}>


          <div style={{
            borderRadius: 10,
            height: 330,
            width: '50%',
            backgroundColor: 'white',
            boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
            marginRight: 10
          }}>

            <ResponsiveRadar
              data={dataProducts}
              keys={[ 'Reclamations']}
              indexBy="taste"
              valueFormat=">-.2f"
              margin={{top: 70, right: 80, bottom: 40, left: 80}}
              borderColor={{from: 'color'}}
              gridLabelOffset={36}
              dotSize={10}
              dotColor={{theme: 'background'}}
              dotBorderWidth={2}
              colors={{scheme: 'nivo'}}
              blendMode="multiply"
              motionConfig="wobbly"
              legends={[
                {
                  anchor: 'top-left',
                  direction: 'column',
                  translateX: -50,
                  translateY: -40,
                  itemWidth: 80,
                  itemHeight: 20,
                  itemTextColor: '#999',
                  symbolSize: 12,
                  symbolShape: 'circle',
                  effects: [
                    {
                      on: 'hover',
                      style: {
                        itemTextColor: '#000'
                      }
                    }
                  ]
                }
              ]}
            />


          </div>

          <div style={{
            borderRadius: 10,
            height: 330,
            width: '50%',
            backgroundColor: 'white',
            boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
            marginRight: 10
          }}>

            <ResponsivePie
              data={dataMerchant}
              margin={{top: 40, right: 80, bottom: 80, left: 80}}
              innerRadius={0.5}
              padAngle={0.7}
              cornerRadius={3}
              activeOuterRadiusOffset={8}
              borderWidth={1}
              borderColor={{
                from: 'color',
                modifiers: [
                  [
                    'darker',
                    0.2
                  ]
                ]
              }}
              arcLinkLabelsSkipAngle={10}
              arcLinkLabelsTextColor="#333333"
              arcLinkLabelsThickness={2}
              arcLinkLabelsColor={{from: 'color'}}
              arcLabelsSkipAngle={10}
              arcLabelsTextColor={{
                from: 'color',
                modifiers: [
                  [
                    'darker',
                    2
                  ]
                ]
              }}
              defs={[
                {
                  id: 'dots',
                  type: 'patternDots',
                  background: 'inherit',
                  color: 'rgba(255, 255, 255, 0.3)',
                  size: 4,
                  padding: 1,
                  stagger: true
                },
                {
                  id: 'lines',
                  type: 'patternLines',
                  background: 'inherit',
                  color: 'rgba(255, 255, 255, 0.3)',
                  rotation: -45,
                  lineWidth: 6,
                  spacing: 10
                }
              ]}
              fill={[
                {
                  match: {
                    id: 'ruby'
                  },
                  id: 'dots'
                },
                {
                  match: {
                    id: 'c'
                  },
                  id: 'dots'
                },
                {
                  match: {
                    id: 'go'
                  },
                  id: 'dots'
                },
                {
                  match: {
                    id: 'python'
                  },
                  id: 'dots'
                },
                {
                  match: {
                    id: 'scala'
                  },
                  id: 'lines'
                },
                {
                  match: {
                    id: 'lisp'
                  },
                  id: 'lines'
                },
                {
                  match: {
                    id: 'elixir'
                  },
                  id: 'lines'
                },
                {
                  match: {
                    id: 'javascript'
                  },
                  id: 'lines'
                }
              ]}
              legends={[
                {
                  anchor: 'bottom',
                  direction: 'row',
                  justify: false,
                  translateX: 0,
                  translateY: 56,
                  itemsSpacing: 0,
                  itemWidth: 100,
                  itemHeight: 18,
                  itemTextColor: '#999',
                  itemDirection: 'left-to-right',
                  itemOpacity: 1,
                  symbolSize: 18,
                  symbolShape: 'circle',
                  effects: [
                    {
                      on: 'hover',
                      style: {
                        itemTextColor: '#000'
                      }
                    }
                  ]
                }
              ]}
            />


          </div>


        </div>
      </div>}
    </div>

  )
    ;
};

export default Overview;
