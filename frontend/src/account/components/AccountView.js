import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';

import { fetchAccounts } from '../actions';

class AccountView extends React.Component {
  componentDidMount() {
    const { id } = this.props.match.params;

    this.props.fetchAccounts(id);
  }

  render() {
    if (!this.props.account) {
      return <div>Loading...</div>;
    }

    const { instituteName, accountNo } = this.props.account;

    return (
      <div>
        <h1>{instituteName}</h1>
        <h5>{accountNo}</h5>
        <Link to="/accounts" className="ui button primary">
          Back to List
        </Link>
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { account: state.accounts[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchAccounts }
)(AccountView);
