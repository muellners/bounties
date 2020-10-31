import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from './user-management';
// prettier-ignore
import bounties, {
  BountiesState
} from 'app/entities/bounties/bounties.reducer';
// prettier-ignore
import bounty, {
  BountyState
} from 'app/entities/bounty/bounty.reducer';
// prettier-ignore
import funding, {
  FundingState
} from 'app/entities/funding/funding.reducer';
// prettier-ignore
import issue, {
  IssueState
} from 'app/entities/issue/issue.reducer';
// prettier-ignore
import profile, {
  ProfileState
} from 'app/entities/profile/profile.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly bounties: BountiesState;
  readonly bounty: BountyState;
  readonly funding: FundingState;
  readonly issue: IssueState;
  readonly profile: ProfileState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  bounties,
  bounty,
  funding,
  issue,
  profile,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
