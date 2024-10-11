import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import { useAppDispatch, useAppSelector } from 'app/config/store';
import { Empty } from 'antd';
//import { searchEntities } from './chercher.reducer';
import {Avatar, Button, Card, Divider, Input, Modal, Typography, Radio, Select} from 'antd';
import { ArrowRightOutlined, UserOutlined } from '@ant-design/icons';
import type { RadioChangeEvent } from 'antd';
import {getEntities as getEntitiesClients} from "app/modules/dashboard/member-user/member-user.reducer";
import moment from "moment";
import 'moment/locale/fr'

const { Search } = Input;
const { Title, Text } = Typography;

const Chercher = () => {
  const navigate = useNavigate();
  const [searchValue, setSearchValue] = useState('');
  const [value, setValue] = useState("PHYSICAL_PERSON");
  const [valueIdentity, setValueIdentity] = useState(null);
  const [modalVisible, setModalVisible] = useState(false);
  const [showResult, setShowResult] = useState(false);
  const [listIdentity, setListIdentity] = useState([]);
  const memberUserList = useAppSelector(state => state.memberUser.entities);
  const dispatch = useAppDispatch();
  //const searchResults = useAppSelector(state => state.search.entities);


  moment.locale('fr')


  const handleSearchChange = (e) => {
    setSearchValue(e.target.value);
    setShowResult(false)
  };

  const onChange = (e: RadioChangeEvent) => {
    setValue(e.target.value);
    setValueIdentity(null)
    setShowResult(false)
  };

  const OnChangeIdentity = (value: string) => {
    setValueIdentity(value);
    setShowResult(false)
  };

const handleSearch = (valueSearch:string) => {
  valueSearch?.trim() !== '' && valueIdentity &&  dispatch(
    getEntitiesClients({
      query:`role.equals=CUSTOMER&acountType.equals=${value}&identifierType.equals=${valueIdentity}&identifierValue.equals=${valueSearch?.trim()}`
    })
  ).then(()=>setShowResult(true));
}


  useEffect(() => {
    if (value) {
      value === "PHYSICAL_PERSON" ? setListIdentity([
        {
          value:"CIN",
          label:`Carte d'identité nationale`
        },
        {
          value:"PASSPORT",
          label:"Passport"
        }
      ])  : setListIdentity([
        {
          value:"TAX_REGISTRATION",
          label:`Matricule fiscale`
        }
      ])
    }

  }, [value]);


  const handleConfirmUser = (id:number) => {
    navigate(`/dashboard/rechercher/AchatProduitProcessus/${id}`);
  };

  const handleNewUser = () => {
    navigate('/dashboard/member-user-commercant/new');
  };



  const renderSearchResults = () => {
      return (
        <Modal
          visible={modalVisible}
          onCancel={() => setModalVisible(false)}
          footer={null}
        >
          <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <h2 style={{ marginBottom: 20 }}>Détails client</h2>
            <Divider />

            {/*{searchResults.map((user, index) => (
              <Card key={index} style={{ width: '100%', backgroundColor: '#F5F5F5', marginBottom: 10 }}>
                <div
                  style={{
                    cursor: 'pointer',
                    display: 'flex',
                    flexDirection: 'row',
                    justifyContent: 'space-between',
                  }}
                >
                  <Avatar shape="square" size={50} icon={<UserOutlined />} />
                  <p style={{ fontSize: 18, marginTop: 10 }}>{user.name}</p>
                  <h2 style={{ marginTop: 5 }}>{user.score}</h2>
                  <Button
                    style={{ backgroundColor: '#FF3029', boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)', marginTop: 10 }}
                    type="primary"
                    onClick={() => navigate(`/dashboard/rechercher/AchatProduitProcessus`)}
                    danger
                  >
                    Faire achat
                  </Button>
                </div>
              </Card>
            ))}*/}
            <Divider />
          </div>
        </Modal>
      );
    return null;
  };

  return (
    <div
      style={{
        fontFamily: 'Segoe UI',
        position: 'relative',
        display: 'flex',
        flexDirection: 'column',
      }}
    >
      <Card
        style={{
          borderRadius: 10,
         // height: '12%',
          width: '100%',
          boxShadow: '0 1px 1px rgba(0, 0, 0, 0.1)',
        }}
      >
        <Title level={2} style={{ textAlign: 'left', marginBottom: 0 }}>
          Recherche client
        </Title>
        <Text type="secondary"> Plongez dans Votre Crédit : Recherchez, Comprenez, Progressez</Text>
      </Card>
      <Card
        style={{
          borderRadius: 10,
          width: '100%',
          marginTop: 30,
          padding: '10px',
          boxShadow: '0 1px 2px rgba(0, 0, 0, 0.1)',
        }}
      >
        <Title level={4} style={{marginBottom:15}}>1- Choisissez le type de compte</Title>
        <Radio.Group onChange={onChange} value={value}>
          <Radio value={"PHYSICAL_PERSON"}>Personne physique</Radio>
          <Radio value={"CORPORATION"}>Société</Radio>
        </Radio.Group>
        <Title level={4} style={{marginBottom:20,marginTop:15}}>2- Recherchez votre client</Title>
        <div style={{display:'flex',flexDirection:'row'}}>
        <Select placeholder={`Pièce d'identité`} style={{ width: 300,height:40,marginRight:10 }} onChange={OnChangeIdentity} value={valueIdentity}>
          {listIdentity.map(identity => (
            <Select.Option value={identity?.value} key={identity?.value}>
              {identity?.label}
            </Select.Option>
          ))}
        </Select>
        <Search
          placeholder="rechercher"
          enterButton="rechercher"
          size="large"
          onSearch={(value) => handleSearch(value)}
          onChange={handleSearchChange}
          style={{ marginBottom: '20px' }}
        />
        </div>

      </Card>
      {showResult &&
        <div style={{marginTop:30}}>
          <Title level={4} style={{marginBottom:20,marginTop:15}}>Résultat de la recherche</Title>
        {memberUserList?.map((item: any, index: number) => (
          <Card
            style={{
              borderRadius: 10,
              width: '100%',
              marginBottom: 20,
              padding: '10px',
              boxShadow: '0 1px 2px rgba(0, 0, 0, 0.1)',
            }}
          >
            <div style={{
              width:'100%',
              display:'flex',
              flexDirection:'row',
            justifyContent:'space-between',
            alignItems:'center'}}>
            <div>
              {item?.acountType === "CORPORATION" && <Text type="secondary">Nom de l'entreprise : <Text
                strong>{item?.businessName?.toUpperCase()}</Text></Text>}<br/>
              <Text type="secondary">Nom & Prénom {item?.acountType === "CORPORATION" ? 'du gérant' : ''} : <Text
                strong>{item?.lastName?.toUpperCase()} {item?.firstName}</Text></Text><br/>
              <Text
                type="secondary">{item?.acountType === "CORPORATION" ? 'Date de création' : 'Date de naissance'} : <Text
                strong>{moment(item?.birthDate).format('ll')}</Text></Text><br/>
            </div>
            <div>
              <Button type="primary" onClick={()=>handleConfirmUser(item?.id)}>Valider</Button>
            </div>
            </div>
          </Card>
        ))}
          {memberUserList?.length === 0 &&    <Card
            style={{
              borderRadius: 10,
              width: '100%',
              marginBottom: 20,
              padding: '10px',
              boxShadow: '0 1px 2px rgba(0, 0, 0, 0.1)',
            }}
          > <div style={{
            width:'100%',
            display:'flex',
            flexDirection:'column',
            justifyContent:'center',
            alignItems:'center'}}>
            <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} style={{marginBottom:0}} description={""} />
            <Title level={4} style={{marginBottom:20}}>Aucun client trouvé</Title>
            <Text
              type="secondary">Vous pouvez créer un nouveau client</Text>
            <Button type="primary" style={{marginTop:5}} onClick={handleNewUser}>Créer un client</Button>
          </div>
          </Card>}
        </div>}

      {renderSearchResults()}
    </div>
  );
};

export default Chercher;
