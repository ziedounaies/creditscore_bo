import dayjs from 'dayjs';
import { IBanks } from 'app/shared/model/banks.model';
import { IContact } from 'app/shared/model/contact.model';
import { IAddress } from 'app/shared/model/address.model';

export interface IAgencies {
  id?: number;
  name?: string | null;
  datefounded?: string | null;
  enabled?: boolean | null;
  createdAt?: dayjs.Dayjs | null;
  banks?: IBanks | null;
  contacts?: IContact[] | null;
  addresses?: IAddress[] | null;
}

export const defaultValue: Readonly<IAgencies> = {
  enabled: false,
};
