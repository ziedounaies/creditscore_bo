import {
  Button,
  Col,
  Typography,
  Transfer,
  Modal,
  Spin,
  Statistic, Select
} from 'antd';
import React, {useEffect, useState} from 'react';
import { useNavigate } from 'react-router-dom';
import { getEntities } from "app/modules/dashboard/banks/banks.reducer";
import { useAppDispatch, useAppSelector } from 'app/config/store';
import {PercentageOutlined} from "@ant-design/icons";
import {getEntity, reset} from "app/modules/dashboard/address/address.reducer";
import {getEntities as getMemberUsers} from "app/entities/member-user/member-user.reducer";
import {getEntities as getBanks} from "app/entities/banks/banks.reducer";
import {getEntities as getAgencies} from "app/entities/agencies/agencies.reducer";
// Import the CSS file

const { Title, Text } = Typography;

interface RecordType {
  key: string;
  title: string;
  description: string;
}

const mockData: RecordType[] = [
  { key: '1', title: 'Historique des paiements ', description: '' },
  { key: '2', title: 'Utilisation du crédit ', description: '' },
  { key: '3', title: 'Ancienneté du crédit ', description: '' },
  { key: '4', title: 'Types de crédit  ', description: '' },
  { key: '5', title: 'Montant du nouveau crédit ', description: '' },
];

const initialTargetKeys = [];

export const algoCreditScore = () => {
  const memberUsers = useAppSelector(state => state.memberUser.entities);
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const banksList = useAppSelector(state => state.banks.entities);
  const [secondCardContent, setSecondCardContent] = useState<string[]>([]); // State for second card content array

  const getAllEntities = () => {
    dispatch(getEntities({}));
  };

  const handleSyncList = () => {
    getAllEntities();
  };
  useEffect(() => {


    dispatch(getMemberUsers({}));
    dispatch(getBanks({}));
    dispatch(getAgencies({}));
  }, []);

  const addContentToSecondCard = (content: string) => {
    if (!secondCardContent.includes(content)) {
      setSecondCardContent(prevContent => [...prevContent, content]);
    }
  };

  const [targetKeys, setTargetKeys] = useState(initialTargetKeys);
  const [selectedKeys, setSelectedKeys] = useState([]);

  const onChange = (nextTargetKeys, direction, moveKeys) => {
    console.log('targetKeys:', nextTargetKeys);
    console.log('direction:', direction);
    console.log('moveKeys:', moveKeys);
    setTargetKeys(nextTargetKeys);
  };

  const onSelectChange = (sourceSelectedKeys, targetSelectedKeys) => {
    console.log('sourceSelectedKeys:', sourceSelectedKeys);
    console.log('targetSelectedKeys:', targetSelectedKeys);
    setSelectedKeys([...sourceSelectedKeys, ...targetSelectedKeys]);
  };

  const onScroll = (direction, e) => {
    console.log('direction:', direction);
    console.log('target:', e.target);
  };

  // Custom render function to apply styles to Transfer items
  const renderItem = (item: RecordType) => ({
    label: (
      <span style={{ fontSize: '18px', fontWeight: 'bold', color: '#333' }}>
        {item.title}

      </span>
    ),
    value: item.key,
  });

  // Modal state and functions
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [isProcessing, setIsProcessing] = useState(false);
  const [creditScore, setCreditScore] = useState<number | null>(null);

  const showModal = () => {
    setIsModalVisible(true);
    setIsProcessing(true);
    setCreditScore(null);
    setTimeout(() => {
      setIsProcessing(false);
      setCreditScore(Math.floor(Math.random() * (900 - 350 + 1)) + 350);
    }, 2000); // Simulating a 2-second processing delay
  };

  const handleOk = () => {
    setIsModalVisible(false);
    // Determine score description based on the creditScore value
    let scoreDescription = '';
    if (creditScore && creditScore >= 250 && creditScore <= 579) {
      scoreDescription = 'Risque élevé';
    } else if (creditScore && creditScore >= 580 && creditScore <= 669) {
      scoreDescription = 'Équitable';
    } else if (creditScore && creditScore >= 670 && creditScore <= 739) {
      scoreDescription = 'Bien';
    } else if (creditScore && creditScore >= 740 && creditScore <= 799) {
      scoreDescription = 'Très bien';
    } else if (creditScore && creditScore >= 800 && creditScore <= 850) {
      scoreDescription = 'Excellent';
    } else {
      scoreDescription = 'Score non disponible ou hors plage';
    }
    // Show a modal or notification with the score description
    Modal.info({
      title: 'Résultat du Calcul de Score',
      content: (
        <div>
          <p>Votre score de crédit : {creditScore}</p>
          <p>Niveau de fiabilité financière : {scoreDescription}</p>
        </div>
      ),
      okButtonProps: {
        style: { backgroundColor: 'red', borderColor: 'red', color: 'white' },
      }, // Add inline styles to customize the OK button
      onOk() {},
    });
  };


  return (
    <div>
      <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between' }}>
        <Col>
          <Title style={{ marginBottom: 0 }} id="banks-heading" data-cy="BanksHeading" level={2}>
            Calcul  Score de Crédit
          </Title>
          <Text type="secondary">
            Évaluez votre fiabilité financière en un instant
          </Text>
        </Col>
        <div className="d-flex justify-content-end">
          <Button style={{ marginTop: 10 }} type="primary" onClick={showModal}>
            Calcule Score de Crédit
          </Button>
        </div>
      </div>



      <Transfer
        dataSource={mockData}
        titles={[  <Select style={{width:"100%"}}>
          <option value="" key="" />
          {memberUsers ? memberUsers.map(user => (
            <option value={user.id} key={user.id}>{user.firstName}</option>
          )) : null}
        </Select>, 'Les critéres choisis']}
        targetKeys={targetKeys}
        selectedKeys={selectedKeys}
        onChange={onChange}
        onSelectChange={onSelectChange}
        onScroll={onScroll}
        render={renderItem}
        style={{ width: '100%', height: '400px', marginTop: 20 }} // Adjusted size
        listStyle={{ width: 750, height: 600, backgroundColor: 'white' }} // Adjust individual list style
      />

      <Modal
        title="Calcul du Score de Crédit"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleOk}
        footer={[
          <Button key="ok" type="primary" onClick={handleOk}>
            OK
          </Button>,
        ]}
      >
        <div style={{ textAlign: 'center', padding: '20px' }}>
          {isProcessing ? (
            <Spin size="large" />
          ) : (
            <div>
              <Statistic
                className="fade-in"
                value={creditScore}
                valueStyle={{
                  fontSize: '48px',
                  fontWeight: 'bold',
                  color: creditScore! >= 700 ? 'green' : 'red'
                }}
              />
            </div>
          )}
        </div>
      </Modal>
    </div>
  );
};

export default algoCreditScore;
