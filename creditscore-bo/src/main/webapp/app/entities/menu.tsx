import React from 'react';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/member-user">
        Member User
      </MenuItem>
      <MenuItem icon="asterisk" to="/address">
        Address
      </MenuItem>
      <MenuItem icon="asterisk" to="/contact">
        Contact
      </MenuItem>
      <MenuItem icon="asterisk" to="/claim">
        Claim
      </MenuItem>
      <MenuItem icon="asterisk" to="/product">
        Product
      </MenuItem>
      <MenuItem icon="asterisk" to="/payment">
        Payment
      </MenuItem>
      <MenuItem icon="asterisk" to="/invoice">
        Invoice
      </MenuItem>
      <MenuItem icon="asterisk" to="/agencies">
        Agencies
      </MenuItem>
      <MenuItem icon="asterisk" to="/banks">
        Banks
      </MenuItem>
      <MenuItem icon="asterisk" to="/credit-rapport">
        Credit Rapport
      </MenuItem>
      <MenuItem icon="asterisk" to="/notification">
        Notification
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
