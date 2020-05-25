import React from 'react';
import { connect } from 'react-redux';
import { fetchDividend, editDividend } from '../actions';
import DividendForm from './DividendForm';

class DividendEdit extends React.Component {
  componentDidMount() {
    this.props.fetchDividend(this.props.match.params.id);
  }

  onSubmit = formValues => {
    this.props.editDividend(this.props.match.params.id, formValues);
  };

  render() {
    if (!this.props.dividend) {
      return <div>Loading...</div>;
    }

    return (
      <DividendForm initialValues={this.props.dividend} onSubmit={this.onSubmit} title="Edit Dividend" />
    );
  }
}

const mapStateToProps = (state, ownProps) => {
  return { dividend: state.dividends[ownProps.match.params.id] };
};

export default connect(
  mapStateToProps,
  { fetchDividend, editDividend }
)(DividendEdit);
