import './home.scss';
import {
  FacebookOutlined,
  TwitterOutlined,
  YoutubeOutlined
} from '@ant-design/icons';
import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
import {ConfigProvider, Space, theme} from 'antd';
import { Row, Col, Alert } from 'reactstrap';
import { TinyColor } from '@ctrl/tinycolor';
import { getLoginUrl, REDIRECT_URL } from 'app/shared/util/url-utils';
import { useAppSelector } from 'app/config/store';
import {Button, Card, Layout, Menu, Statistic, Typography} from "antd";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";


const { Header, Content, Footer, } = Layout;

function ArrowUpOutlined() {
  return null;
}
const { Title,Text } = Typography;
export const Home = () => {

  const {
    token: { colorBgContainer, borderRadiusLG },
  } = theme.useToken();

  const account = useAppSelector(state => state.authentication.account);
  useEffect(() => {
    const redirectURL = localStorage.getItem(REDIRECT_URL);
    if (redirectURL) {
      localStorage.removeItem(REDIRECT_URL);
      location.href = `${location.origin}${redirectURL}`;
    }
  });

  const colors1 = ['#6253E1', '#04BEFE'];
  const colors2 = ['#fc6076', '#ff9a44', '#ef9d43', '#e75516'];
  const colors3 = ['#40e495', '#30dd8a', '#2bb673'];
  const getHoverColors = (colors: string[]) =>
    colors.map((color) => new TinyColor(color).lighten(5).toString());
  const getActiveColors = (colors: string[]) =>
    colors.map((color) => new TinyColor(color).darken(5).toString());

  useEffect(() => {
    const redirectURL = localStorage.getItem(REDIRECT_URL);
    if (redirectURL) {
      localStorage.removeItem(REDIRECT_URL);
      location.href = `${location.origin}${redirectURL}`;
    }
  }, []);

  return (
    <div style={{
      background: 'white)',
    }}>
      <div style={{ borderRadius: 10}}>
        <div className="logo"/>
        <div style={{display: 'flex', flexDirection: 'row', justifyContent: 'space-between',}}>

          <img src="content/images/10.png" alt="Logo" width='20%' height='20%' style={{marginTop: -50}}/>


          <ConfigProvider
            theme={{
              components: {
                Button: {
                  colorPrimary: `linear-gradient(135deg, ${colors1.join(', ')})`,
                  colorPrimaryHover: `linear-gradient(135deg, ${getHoverColors(colors1).join(', ')})`,
                  colorPrimaryActive: `linear-gradient(135deg, ${getActiveColors(colors1).join(', ')})`,
                  lineWidth: 0,
                },
              },
            }}
          >
            <Button type="primary" size="large" style={{marginTop: 25, marginRight: 25}} href={'/dashboard'}>
              se connecter
            </Button>
          </ConfigProvider>

        </div>

      </div>


      <div style={{padding: '0 50px', height: '500px', background: 'linear-gradient(to right, #f2f2f2, #e8e8e8)'}}>
        <div className="site-layout-content">

          <div style={{
            display: 'flex',
            flexDirection: 'row',
            justifyContent: 'space-around',
            alignItems: 'center',
            height: '100%',

          }}>
            <div style={{
              padding: '0 50px',
              height: '500px',
              background: 'linear-gradient(to right, #f2f2f2, #e8e8e8)',
              display: 'flex',
              justifyContent: 'center',
              alignItems: 'center'
            }}>
              <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center', textAlign: 'center',marginRight:500}}>
                <Title level={1} style={{fontSize: '48px', fontWeight: 'bold', color: '#2c3e50', marginBottom: '10px'}}>
                  Bienvenue !
                </Title>
                <Title level={2} style={{fontSize: '36px', color: '#34495e', marginBottom: '10px'}}>
                  Votre portail de gestion de crédit
                </Title>
                <Title level={3} style={{fontSize: '24px', fontStyle: 'italic', color: '#7f8c8d'}}>
                  Connectez-vous pour évaluer votre score de crédit.
                </Title>
              </div>
              <div
                style={{marginTop: 50, position: 'relative', width: '320px', height: '300px', borderRadius: '50%', overflow: 'hidden'}}>
                <img src="content/images/2222.png" alt="Logo" width='150%' height='120%' style={{
                  position: 'absolute',

                  top: '50%',
                  left: '50%',
                  transform: 'translate(-50%, -50%)',
                  boxShadow: '0px 5px 10px rgba(0, 0, 0, 0.3)'
                }}/>
                <div style={{
                  position: 'absolute',
                  top: 0,
                  left: 0,
                  width: '100%',
                  height: '100%',
                  background: 'linear-gradient(to bottom, rgba(0, 0, 0, 0.2), rgba(0, 0, 0, 0))'
                }}></div>

              </div>
            </div>
          </div>

        </div>
      </div>



      <div style={{
        height: 200, textAlign: 'center',
      }}>

        <div style={{
          display: 'flex',
          flexDirection: 'row',
          justifyContent: 'space-around',
          padding: '20px',
          backgroundColor: '#f9f9f9',
          fontFamily: 'Arial, sans-serif'
        }}>

          <div style={{display: 'flex', flexDirection: 'row',justifyContent:'space-between', gap: '40px'}}>

            <div style={{display: 'flex', flexDirection: 'column', gap: '10px', width: '200px'}}>
              <p style={{fontWeight: 'bold', margin: '0', textAlign: 'justify'}}>INFORMATION D'ENTREPRISE</p>
              <p style={{textAlign: 'justify'}}>A propos Credit Wise</p>
              <p style={{textAlign: 'justify'}}>Carrières</p>
              <p style={{textAlign: 'justify'}}>Dans les nouvelles</p>
              <p style={{textAlign: 'justify'}}>Blogue d'ingénierie</p>
            </div>

            <div style={{display: 'flex', flexDirection: 'column', gap: '10px', width: '200px'}}>
              <p style={{fontWeight: 'bold', margin: '0', textAlign: 'justify'}}>HELP</p>
              <p style={{textAlign: 'justify'}}>centre d'aide</p>
              <p style={{textAlign: 'justify'}}>Comment fonctionne le crédit Wise ?</p>
              <p style={{textAlign: 'justify'}}>Pratiques de sécurité</p>
              <p style={{textAlign: 'justify'}}>Directives éditoriales</p>
            </div>

            <div style={{display: 'flex', flexDirection: 'column', gap: '10px', width: '200px'}}>
              <p style={{fontWeight: 'bold', margin: '0', textAlign: 'justify'}}>LÉGAL</p>
              <p style={{textAlign: 'justify'}}>politique de confidentialité</p>
              <p style={{textAlign: 'justify'}}>Politique de confidentialité de CA</p>
              <p style={{textAlign: 'justify'}}>Conditions d'utilisation</p>
              <p style={{textAlign: 'justify'}}>Préférences de données</p>
            </div>

            <div style={{display: 'flex', flexDirection: 'column', gap: '10px', width: '200px'}}>
              <p style={{fontWeight: 'bold', margin: '0', textAlign: 'justify'}}>LIENS NOTABLES</p>
              <p style={{textAlign: 'justify'}}>Informations sur les cartes de crédit</p>
              <p style={{textAlign: 'justify'}}>7 façons de trouver une assurance auto bon marché</p>
              <p style={{textAlign: 'justify'}}>7 prêts automobiles pour mauvais crédit</p>
              <p style={{textAlign: 'justify'}}>Meilleures cartes de crédit à limite élevée</p>
            </div>

            <div style={{display: 'flex', flexDirection: 'column', gap: '10px', width: '200px'}}>
              <p style={{fontWeight: 'bold', margin: '0', textAlign: 'justify'}}>SOCIALE</p>
              <p style={{textAlign: 'justify'}}><FacebookOutlined/> Facebook</p>
              <p style={{textAlign: 'justify'}}><TwitterOutlined/> Twitter</p>
              <p style={{textAlign: 'justify'}}><YoutubeOutlined/> Youtube</p>
            </div>

          </div>


        </div>

        ©2024 Votre entreprise. Tous droits réservés.

      </div>
    </div>
  )
    ;
};

export default Home;
