import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import Modal from '../../app/components/Modal';
import history from '../../app/components/history';
import { fetchTransaction, deleteTransaction } from '../actions';

class TransactionDelete extends React.Component {
  componentDidMount() {
    this.props.fetchTransaction(this.props.match.params.id);
  }

  renderActions() {
    const { id } = this.props.match.params;

    return (
      <React.Fragment>
        <button
          onClick={() => this.props.deleteTransaction(id)}
          className="ui button negative"
        >
          Delete
        </button>
        <Link to="/transactions" className="ui button">
          Cancel
        </Link>
      </React.Fragment>
    );
  }

  renderContent() {
    return 'Are you sure you want to reverse this transaction?';
  }

  render() {
    return (
      <Modal
        title="Delete Transaction"
        content={this.renderContent()}
        actions={this.renderActions()}
        onDismiss={() => history.push('/transactions')}
      />
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { transaction: state.transactions[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchTransaction, deleteTransaction }
)(TransactionDelete);
