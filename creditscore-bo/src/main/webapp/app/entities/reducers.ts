import memberUser from 'app/entities/member-user/member-user.reducer';
import address from 'app/entities/address/address.reducer';
import contact from 'app/entities/contact/contact.reducer';
import claim from 'app/entities/claim/claim.reducer';
import product from 'app/entities/product/product.reducer';
import payment from 'app/entities/payment/payment.reducer';
import invoice from 'app/entities/invoice/invoice.reducer';
import agencies from 'app/entities/agencies/agencies.reducer';
import banks from 'app/entities/banks/banks.reducer';
import creditRapport from 'app/entities/credit-rapport/credit-rapport.reducer';
import notification from 'app/entities/notification/notification.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  memberUser,
  address,
  contact,
  claim,
  product,
  payment,
  invoice,
  agencies,
  banks,
  creditRapport,
  notification,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
