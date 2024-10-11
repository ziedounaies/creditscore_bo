import React, {useEffect, useState} from 'react';
import {Table, Input, Button, Card, Form, Modal, Select, Typography, Tag, DatePicker} from 'antd';
import {useAppDispatch, useAppSelector} from "app/config/store";
import {getEntities as getEntitiesProducts} from "app/modules/dashboard/product/product.reducer";
import {getSession} from "app/shared/reducers/authentication";
import {getProfile} from "app/shared/reducers/application-profile";
import {useParams} from "react-router-dom";
import {getEntity} from "app/modules/dashboard/member-user/member-user.reducer";
import {BankOutlined, BarcodeOutlined, DollarOutlined, UserOutlined} from "@ant-design/icons";
import dayjs from "dayjs";
import {forEach} from "lodash";
import {createEntity} from "app/modules/dashboard/payment/payment.reducer";
import {createEntity as createEntityInvoice} from "app/modules/dashboard/invoice/invoice.reducer";


const {Search} = Input;
const {Title, Text} = Typography;

const AchatProduitProcessus = () => {
  const [produitData, setProduitData] = useState([]);
  const [paiementData, setPaiementData] = useState([]);
  const [paiementDataEcheance, setPaiementDataEcheance] = useState([]);
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [form] = Form.useForm();
  const products = useAppSelector(state => state.product.entities);
  // États pour les champs de saisie
  const [name, setName] = useState('');
  const [age, setAge] = useState('');
  const [address, setAddress] = useState('');
  const [price, setPrice] = useState('');
  const [selectedProduct, setSelectedProduct] = useState(null);
  const dispatch = useAppDispatch()
  const account = useAppSelector(state => state.authentication.account);

  const {id} = useParams<'id'>();

  const memberUserEntity = useAppSelector(state => state.memberUser.entity);

  const [userData, setUserData] = useState([]);

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  useEffect(() => {

    if (form.getFieldValue('paymentMethod')) {
      let data = []
      for (let i = 0; i < parseInt(form.getFieldValue('nbrPayment')); i++) {
        let paiementObj = {
          amount: (produitData.reduce((partialSum, a) => parseFloat(partialSum) + parseFloat(a?.total), 0) / parseInt(form.getFieldValue('nbrPayment')))?.toFixed(2),
          expectedPaymentDate: dayjs(new Date()).add(i, 'month'),
        }
        data = [...data, paiementObj]
      }
      console.log(data)
      form.setFieldValue('paiementDataEcheance', data)

    }
  }, [Form.useWatch('paymentMethod', form)]);

  const handleNumberCheck = (numberCheck:string,key:number) => {
    let list = form.getFieldValue('paiementDataEcheance');

    list[key]={...list[key],checkNumber:numberCheck}

    form.setFieldValue('paiementDataEcheance',list)
  }


  useEffect(() => {
    memberUserEntity && setUserData([{
      ...memberUserEntity,
      fullName: `${memberUserEntity?.lastName?.toUpperCase()} ${memberUserEntity?.firstName}`
    }])
  }, [memberUserEntity]);

  const {Option} = Select;
  // Fonction pour gérer l'ajout d'un produit
  const handleAddProduit = () => {
    // Créer un nouvel objet produit avec les valeurs actuelles des champs
    const newProduit = {
      key: produitData.length + 1, // Génération d'une clé unique pour l'élément ajouté
      name,
      age,
      address,
      price,
      total:0
    };

    // Ajouter le nouvel objet produit à la liste existante
    setProduitData([...produitData, newProduit]);

    // Réinitialiser les champs après l'ajout
    setName('');
    setAge('');
    setAddress('');
    setPrice('');
  };

  const handleAddPaiementRow = () => {
    setIsModalVisible(true);
    form.setFieldValue('recipient', account?.login)
  };

  const handleOk = () => {
    const values = form.getFieldsValue();

    console.log(form.getFieldValue('paiementDataEcheance'))

    // const newData = {
    //   key: (paiementData.length + 1).toString(),
    //   ...values,
    // };


    setPaiementData(form.getFieldValue('paiementDataEcheance')?.map((item, index) => (
      {
        key: (index + 1).toString(),
        ...item,
        ...values,
        dateOfSignature:form.getFieldValue('paymentMethod') === "CHECK" ? dayjs(new Date())?.toString() : null,
        checkDate: form.getFieldValue('paymentMethod') === "CHECK" ? dayjs(new Date()) : null

      }
    )));
    setIsModalVisible(false);
    form.resetFields();

  };

  const handleCancel = () => {
    setIsModalVisible(false);
    form.resetFields();
  };


  useEffect(() => {
    dispatch(
      getEntitiesProducts({})
    );
  }, []);

  const handleConfirm = () => {
    dispatch(createEntityInvoice({
      amount : produitData.reduce((partialSum, a) => parseFloat(partialSum) + parseFloat(a?.total), 0)?.toFixed(2)?.toString() ,

    invoiceNumber: `CW-${Math.random().toString(36).slice(2, 7)}`,
      memberUser: {
        id: memberUserEntity?.id
    },
    status : "PENDING",
    }));
    paiementData?.map((item:any,index:number)=>(
      dispatch(createEntity({
        ...item,
        status: 'PENDING',
        currency:'TND',
        checkIssuer:`${memberUserEntity?.firstName} ${memberUserEntity?.lastName?.toUpperCase()}`,
        dateOfSignature: dayjs(new Date()),
        memberUser:{
          id:id
        }
      }))
    ))

  }


  const columns = [
    {
      title: 'Nom produit',
      dataIndex: 'id',
      render: (_, record) => (
        <Select
          placeholder="Sélectionnez un produit"
          value={record?.id}
          onChange={(value) => {
            const newData = [...produitData];
            newData[record.key - 1] = {
              ...products.find(it => it.id.toString() === value.toString()),
              key: record.key,
              qte: 1,
              total: products.find(it => it.id.toString() === value.toString())?.price
            }
            console.log(newData)
            setProduitData(newData);
          }}
          style={{width: '100%'}}
        >
          {products
            ? products.map(otherEntity => (
              <Option value={otherEntity.id} key={otherEntity.id}
                      disabled={produitData.some((item, index) => item?.id === otherEntity.id)}>
                {otherEntity.name}
              </Option>
            ))
            : null}
        </Select>
      ),
    },
    {
      title: 'Numéro série',
      dataIndex: 'serialNumber',

    },
    {
      title: 'Garantie',
      dataIndex: 'guarantee',
    },
    {
      title: 'Prix',
      dataIndex: 'price',
      render: (text) => text && `${text?.replace(/\B(?=(\d{3})+(?!\d))/g, " ")} TND`,
    },
    {
      title: 'Qte',
      dataIndex: 'qte',
      render: (_, record) => (
        <Input placeholder="Numéro cheque" value={record.qte}
               type="number"
               onChange={(e) => {
                 const newData = [...produitData];
                 newData[record.key - 1] = {
                   ...record,
                   qte: e.target.value,
                   total: (parseInt(e.target.value) * parseFloat(record?.price))?.toFixed(2)
                 }
                 console.log(newData)
                 setProduitData(newData);
               }}/>
      ),
    },
    {
      title: 'Prix Total',
      dataIndex: 'total',
      width: 150,
      render: (text) => text && `${text?.replace(/\B(?=(\d{3})+(?!\d))/g, " ")} TND`,
    },
  ];

  const columnsPaiement = [
    {
      title: 'Méthode paiement',
      dataIndex: 'paymentMethod',
      render: (text) =>
        text === 'CHECK' ? "Chéque" :
          text === 'CASH' ? "Espèce" :
            "Débit",
    },
    {
      title: 'Numéro compte',
      dataIndex: 'accountNumber',
      render: (text) => text ? text : '-',
    },
    {
      title: 'Numéro chéque',
      dataIndex: 'checkNumber',
      width: 120,
      render: (text) => text ? text : '-',
    },
    // {
    //   title: 'Date chéque',
    //   dataIndex: 'checkDate',
    //   render: (text) => text ? text : '-',
    // },
    {
      title: 'Date de signature',
      dataIndex: 'dateOfSignature',
      render: (text) => text ? text : '-',
    },
    {
      title: 'Destinataire',
      dataIndex: 'recipient',
      render: (text) => text ? text : '-',
    },

    {
      title: 'Montant',
      dataIndex: 'amount',
      render: (text) => text && `${text?.replace(/\B(?=(\d{3})+(?!\d))/g, " ")} TND`,

    },
    {
      title: 'Date paiement prévue',
      dataIndex: 'expectedPaymentDate',
      render: (text) => text?.toString(),
    },
  ];

  const columnsUser = [
    {
      title: 'Nom & Prénom',
      dataIndex: 'fullName',

    },
    {
      title: `Pièce d'identité`,
      dataIndex: 'identifierType',
      render: (text: string) =>
        text === 'CIN' ? `Carte d'identité nationale` : text === 'PASSPORT' ? `Passeport` : `Matricule fiscale`,
    },
    {
      title: `N° pièce d'identité`,
      dataIndex: 'identifierValue',
    },
    {
      title: 'Score de crédit',
      dataIndex: 'CreditScore',
    },
  ];


  return (
    <div>
      <Card
        style={{
          marginBottom: 10,
          borderRadius: 10,
          height: '12%',
          width: '100%',
          boxShadow: '0 1px 2px rgba(0, 0, 0, 0.1)',
        }}
      >
        <div
          style={{
            display: 'flex',
            flexDirection: 'row',
            justifyContent: 'space-between',
          }}
        >
          <Title level={2} style={{textAlign: 'left', marginBottom: 0}}>
            Processus d'achat des produits
          </Title>
          <Button
            disabled={paiementData?.length === 0}
            type="primary"
            onClick={() =>handleConfirm()}
          >
            Confirmer achat
          </Button>
        </div>
      </Card>

      <Card style={{marginBottom: 10}} title="Informations du client">
        <Table columns={columnsUser} dataSource={userData} size="small" pagination={false}/>
      </Card>

      <Card style={{marginBottom: 10}} title="Choix du produit">
        <div
          style={{display: 'flex', flexDirection: 'row', justifyContent: 'flex-end', marginTop: -65, marginBottom: 20}}>
          <Button type="primary" onClick={handleAddProduit} disabled={produitData?.length === products?.length}>
            Ajouter produit
          </Button></div>
        <Table columns={columns} dataSource={produitData} size="small" pagination={false}/>

      </Card>

      <Card title="Echéance de paiement">
        <div
          style={{
            display: 'flex',
            flexDirection: 'row',
            justifyContent: 'flex-end',
            marginTop: -65,
            marginBottom: 26,
          }}
        >
          <Button type="primary" onClick={handleAddPaiementRow} disabled={produitData?.length === 0}>
            Ajouter paiement
          </Button>
        </div>
        <Table columns={columnsPaiement} dataSource={paiementData} size="small" pagination={false}/>

        <Modal
          visible={isModalVisible}
          onOk={handleOk}
          onCancel={handleCancel}
          cancelText="Retour"
          okText="Confirmer"
          width={"40%"}
        >
          <Form form={form} layout="vertical">
            <div style={{marginBottom: 15}}>
              <Title level={4} style={{marginBottom: 5}}>En combien de fois voulez vous diviser votre paiement</Title>
              <Text type="secondary">Vous pouvez diviser votre facture en plusieurs trânches selon votre choix</Text>
            </div>
            {/* PAYMENT*/}
            <Form.Item
              name="nbrPayment"
              label="Nombre de paiements"
              rules={[{required: true, message: 'Veuillez renseigner ce champ!',}]}
            >

              <Select placeholder="Nombre de paiements">


                <Select.Option value={"1"} key={"1"}>
                  1 fois
                </Select.Option>
                <Select.Option value={"3"} key={"3"}>
                  3 fois

                </Select.Option>
                <Select.Option value={"6"} key={"6"}>

                  6 fois
                </Select.Option>
                <Select.Option value={"12"} key={"12"}>

                  12 fois
                </Select.Option>
              </Select>
            </Form.Item>

            <Form.Item
              noStyle
              shouldUpdate={(prevValues, currentValues) => prevValues.nbrPayment !== currentValues.nbrPayment}
            >
              {({getFieldValue}) =>
                getFieldValue('nbrPayment') && (
                  <>
                    <div style={{marginBottom: 15}}>
                      <Title level={4} style={{marginBottom: 5}}>Veuillez choisir votre méthode de paiement</Title>
                      <Text type="secondary">Vous pouvez effectuer vos paiement par éspece, par chèque ou bien par
                        prélèvement</Text>
                    </div>
                    {/* PAYMENT*/}
                    <Form.Item
                      name="paymentMethod"
                      label="Méthode de paiement"
                      rules={[{required: true, message: 'Veuillez renseigner ce champ!',}]}
                    >

                      <Select placeholder="Methode de paiement">


                        <Select.Option value={"CHECK"} key={"CHECK"}>
                          Chéque
                        </Select.Option>
                        <Select.Option value={"CASH"} key={"CASH"}>
                          Espèce

                        </Select.Option>
                        <Select.Option value={"DEBIT"} key={"DEBIT"}>

                          Débit
                        </Select.Option>
                      </Select>
                    </Form.Item>
                    <Form.Item
                      name="recipient"
                      label="Destinataire"
                      rules={[{required: true, message: 'Veuillez renseigner ce champ!'}]}
                    >
                      <Input prefix={<UserOutlined/>} placeholder="Destinataire" disabled/>
                    </Form.Item>
                  </>
                )
              }
            </Form.Item>


            {/* recipient */}
            <Form.Item
              noStyle
              shouldUpdate={(prevValues, currentValues) => prevValues.paymentMethod !== currentValues.paymentMethod}
            >
              {({getFieldValue}) =>
                getFieldValue('paymentMethod') && (
                  <>
                    <div style={{marginBottom: 15}}>
                      <Title level={4} style={{marginBottom: 5}}>Ci-dessous le tableau des échéances pour le montant de
                        : {produitData.reduce((partialSum, a) => parseFloat(partialSum) + parseFloat(a?.total), 0)?.toFixed(2)?.toString()?.replace(/\B(?=(\d{3})+(?!\d))/g, " ")} TND</Title>
                      <Text type="secondary">Vous pouvez effectuer vos paiement par éspece, par chèque ou bien par
                        prélèvement</Text>
                    </div>

                    {/* ACCOUNT NUMBER */}
                    {getFieldValue('paymentMethod') === "DEBIT" && (<Form.Item
                      name="accountNumber"
                      label="Numéro compte"
                      rules={[{required: true, message: 'Veuillez renseigner ce champ!'}]}
                      required={true}
                    >
                      <Input prefix={<BankOutlined/>} placeholder="Numéro compte" required/>
                    </Form.Item>)}

                    <Form.Item
                      noStyle
                      shouldUpdate={(prevValues, currentValues) => prevValues.paiementDataEcheance !== currentValues.paiementDataEcheance}
                    >
                      {({getFieldValue}) =>
                        getFieldValue('paiementDataEcheance')?.map((item, index) => (
                          <div
                            style={{
                              display: 'flex',
                              flexDirection: "row",
                              justifyContent: "space-between",
                              width: "100%"
                            }}>


                            {/* CHECK NUMBER */}
                            {getFieldValue('paymentMethod') === "CHECK" &&
                              <div style={{display: 'flex', flexDirection: "column", flex: 1, marginBottom: 15}}>

                                <Text>Numéro de chéque</Text>
                                <Input prefix={<BarcodeOutlined/>} style={{width: '95%'}} placeholder="Numero cheque" type='number' onChange={(e:any)=>handleNumberCheck(e.target.value,index)}/>
                              </div>}

                            {/* amount */}
                            <div style={{display: 'flex', flexDirection: "column", flex: 1, marginBottom: 15}}>

                              <Text>Montant - {index + 1}</Text>
                              <Input prefix={<DollarOutlined/>} style={{width: '95%'}} placeholder="Montant" disabled
                                     value={item?.amount} addonAfter="TND"/>
                            </div>
                            {/* expectedPaymentDate */}
                            <div style={{display: 'flex', flexDirection: "column", flex: 1, marginBottom: 15}}>
                              <Text>Date de paiement expecté</Text>
                              <DatePicker style={{width: '95%'}} format="YYYY-MM-DD"
                                          placeholder="Sélectionnez une date"
                                          value={item?.expectedPaymentDate}
                                          disabled/>
                            </div>

                          </div>
                        ))}
                    </Form.Item>

                  </>
                )
              }
            </Form.Item>


          </Form>
        </Modal>
      </Card>
    </div>
  );
};

export default AchatProduitProcessus;
