import dayjs from 'dayjs';
import { IMemberUser } from 'app/shared/model/member-user.model';
import { IBanks } from 'app/shared/model/banks.model';
import { IAgencies } from 'app/shared/model/agencies.model';
import { TypeContact } from 'app/shared/model/enumerations/type-contact.model';

export interface IContact {
  id?: number;
  type?: keyof typeof TypeContact | null;
  value?: string | null;
  createdAt?: dayjs.Dayjs | null;
  memberUser?: IMemberUser | null;
  banks?: IBanks | null;
  agencies?: IAgencies | null;
}

export const defaultValue: Readonly<IContact> = {};
