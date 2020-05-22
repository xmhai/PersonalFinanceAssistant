import React from 'react';
import { connect } from 'react-redux';
import { fetchAccount, editAccount } from '../actions';
import AccountForm from './AccountForm';

class AccountEdit extends React.Component {
  componentDidMount() {
    this.props.fetchAccount(this.props.match.params.id);
  }

  onSubmit = formValues => {
    this.props.editAccount(this.props.match.params.id, formValues);
  };

  render() {
    if (!this.props.account) {
      return <div>Loading...</div>;
    }

    return (
      <div>
        <h3>Edit Account</h3>
        <AccountForm
          initialValues={this.props.account}
          onSubmit={this.onSubmit}
        />
      </div>
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { account: state.accounts[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchAccount, editAccount }
)(AccountEdit);
