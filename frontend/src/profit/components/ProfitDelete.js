import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import Modal from '../../app/components/Modal';
import history from '../../app/components/history';
import { fetchProfit, deleteProfit } from '../actions';

class ProfitDelete extends React.Component {
  componentDidMount() {
    this.props.fetchProfit(this.props.match.params.id);
  }

  renderActions() {
    const { id } = this.props.match.params;

    return (
      <React.Fragment>
        <button
          onClick={() => this.props.deleteProfit(id)}
          className="ui button negative"
        >
          Delete
        </button>
        <Link to="/profits" className="ui button">
          Cancel
        </Link>
      </React.Fragment>
    );
  }

  renderContent() {
    if (!this.props.profit) {
      return 'Are you sure you want to delete this profit?';
    }

    return `Are you sure you want to delete this profit: ${this.props.profit.name} ${this.props.profit.code}`;
  }

  render() {
    return (
      <Modal
        title="Delete Profit"
        content={this.renderContent()}
        actions={this.renderActions()}
        onDismiss={() => history.push('/profits')}
      />
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { profit: state.profits[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchProfit, deleteProfit }
)(ProfitDelete);
