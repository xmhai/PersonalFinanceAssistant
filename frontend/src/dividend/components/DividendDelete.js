import React from 'react';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import Modal from '../../app/components/Modal';
import history from '../../app/components/history';
import { fetchDividend, deleteDividend } from '../actions';

class DividendDelete extends React.Component {
  componentDidMount() {
    this.props.fetchDividend(this.props.match.params.id);
  }

  renderActions() {
    const { id } = this.props.match.params;

    return (
      <React.Fragment>
        <button
          onClick={() => this.props.deleteDividend(id)}
          className="ui button negative"
        >
          Delete
        </button>
        <Link to="/dividends" className="ui button">
          Cancel
        </Link>
      </React.Fragment>
    );
  }

  renderContent() {
    if (!this.props.dividend) {
      return 'Are you sure you want to delete this dividend?';
    }

    return `Are you sure you want to delete this dividend: ${this.props.dividend.name} ${this.props.dividend.code}`;
  }

  render() {
    return (
      <Modal
        title="Delete Dividend"
        content={this.renderContent()}
        actions={this.renderActions()}
        onDismiss={() => history.push('/dividends')}
      />
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { dividend: state.dividends[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchDividend, deleteDividend }
)(DividendDelete);
