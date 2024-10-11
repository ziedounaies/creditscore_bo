import dayjs from 'dayjs';
import { IMemberUser } from 'app/shared/model/member-user.model';
import { IBanks } from 'app/shared/model/banks.model';
import { IAgencies } from 'app/shared/model/agencies.model';

export interface IAddress {
  id?: number;
  street?: string | null;
  city?: string | null;
  zipCode?: string | null;
  createdAt?: dayjs.Dayjs | null;
  memberUser?: IMemberUser | null;
  banks?: IBanks | null;
  agencies?: IAgencies | null;
}

export const defaultValue: Readonly<IAddress> = {};
