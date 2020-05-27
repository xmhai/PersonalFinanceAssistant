import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import Modal from '../../app/components/Modal';
import history from '../../app/components/history';
import { fetchStock, deleteStock } from '../actions';

class StockDelete extends React.Component {
  componentDidMount() {
    this.props.fetchStock(this.props.match.params.id);
  }

  renderActions() {
    const { id } = this.props.match.params;

    return (
      <React.Fragment>
        <button
          onClick={() => this.props.deleteStock(id)}
          className="ui button negative"
        >
          Delete
        </button>
        <Link to="/stocks" className="ui button">
          Cancel
        </Link>
      </React.Fragment>
    );
  }

  renderContent() {
    if (!this.props.stock) {
      return 'Are you sure you want to delete this stock?';
    }

    return `Are you sure you want to delete this stock: ${this.props.stock.name} ${this.props.stock.code}`;
  }

  render() {
    return (
      <Modal
        title="Delete Stock"
        content={this.renderContent()}
        actions={this.renderActions()}
        onDismiss={() => history.push('/stocks')}
      />
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { stock: state.stocks[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchStock, deleteStock }
)(StockDelete);
