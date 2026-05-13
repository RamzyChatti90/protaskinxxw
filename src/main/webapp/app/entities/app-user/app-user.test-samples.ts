import { IAppUser, NewAppUser } from './app-user.model';

export const sampleWithRequiredData: IAppUser = {
  id: 19407,
  firstName: 'Ralwmap',
  lastName: 'Cob',
};

export const sampleWithPartialData: IAppUser = {
  id: 7641,
  firstName: 'Rizywdt',
  lastName: 'Rabxhkr',
  avatarUrl: 'https:wLM',
  phone: '+08910',
};

export const sampleWithFullData: IAppUser = {
  id: 1902,
  firstName: 'Dquofe',
  lastName: 'Ktlm',
  avatarUrl: 'http:D~%',
  bio: 'insipide',
  phone: '3174',
};

export const sampleWithNewData: NewAppUser = {
  firstName: 'Tbgwpn',
  lastName: 'Hyo',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
