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
  phone: '+ 55',
  avatarUrl: 'https:#iq.!',
};

export const sampleWithFullData: IAppUser = {
  id: 1902,
  firstName: 'Dquofe',
  lastName: 'Ktlm',
  phone: '4 0',
  bio: '../fake-data/blob/hipster.txt',
  avatarUrl: 'http:SzkI',
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
