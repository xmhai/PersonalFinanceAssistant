import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import Modal from '../../app/components/Modal';
import history from '../../app/components/history';
import { fetchAccount, deleteAccount } from '../actions';

class AccountDelete extends React.Component {
  componentDidMount() {
    this.props.fetchAccount(this.props.match.params.id);
  }

  renderActions() {
    const { id } = this.props.match.params;

    return (
      <React.Fragment>
        <button
          onClick={() => this.props.deleteAccount(id)}
          className="ui button negative"
        >
          Delete
        </button>
        <Link to="/accounts" className="ui button">
          Cancel
        </Link>
      </React.Fragment>
    );
  }

  renderContent() {
    if (!this.props.account) {
      return 'Are you sure you want to delete this account?';
    }

    return `Are you sure you want to delete this account: ${this.props.account.instituteName} ${this.props.account.accountNo}`;
  }

  render() {
    return (
      <Modal
        title="Delete Account"
        content={this.renderContent()}
        actions={this.renderActions()}
        onDismiss={() => history.push('/accounts')}
      />
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { account: state.accounts[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchAccount, deleteAccount }
)(AccountDelete);
